# Pi needs to be SSHed into in order for the robot to be enabled

import asyncio
import time
import ntcore

from viam.robot.client import RobotClient
from viam.rpc.dial import Credentials, DialOptions
from viam.components.movement_sensor import MovementSensor

class PiCode:
    def __init__(self) -> None:
        # get the default instance of NetworkTables
        inst = ntcore.NetworkTableInstance.getDefault()

        datatable = inst.getTable("MPU Data")
        self.dataPub = datatable.getDoubleArrayTopic("Data").publish()
        self.recalibrateFlag = datatable.getBooleanTopic("Recalibrate").getEntry(False)
        self.prev = 0
        self.totalAngleChangeX = 0
        self.totalAngleChangeY = 0
        self.totalAngleChangeZ = 0
        self.averageDriftX = 0
        self.averageDriftY = 0
        self.averageDriftZ = 0

    async def connect(self):
        creds = Credentials(
            type='api-key',
            payload='hello')        
        opts = RobotClient.Options(
            dial_options=DialOptions(credentials=creds, insecure=True, disable_webrtc=True),
        )
        return await RobotClient.at_address('127.0.0.1:8080', opts)
    
    async def recalibrate(self, gyro):
        driftX = 0
        driftY = 0
        driftZ = 0
        calibration = int(1 / 0.012)
        for i in range(calibration):
            startTime = time.time()
            angularVelReading = await gyro.get_angular_velocity()
            driftX += angularVelReading.x
            driftY += angularVelReading.y
            driftZ += angularVelReading.z
        self.totalAngleChangeX = 0
        self.totalAngleChangeY = 0
        self.totalAngleChangeZ = 0
        self.averageDriftX = driftX / calibration
        self.averageDriftY = driftY / calibration
        self.averageDriftZ = driftZ / calibration
        self.recalibrateFlag.set(False)

    async def main(self):
        for connectAttempt in range(10):
            try:
                robot = await self.connect()
                break
            except ConnectionRefusedError:
                time.sleep(10)

        print('Resources:')
        print(robot.resource_names)

        gyro = MovementSensor.from_robot(robot, 'IMU')
        await self.recalibrate(gyro)

        # create a NetworkTable instance
        inst = ntcore.NetworkTableInstance.getDefault()

        # start a NT4 client
        inst.startClient4("7272 Pi")

        # connect to a roboRIO with team number TEAM
        inst.setServerTeam(7272)

        # starting a DS client will try to get the roboRIO address from the DS application
        inst.startDSClient()

        # connect to a specific host/port
        inst.setServer("10.72.72.10:1250", ntcore.NetworkTableInstance.kDefaultPort4)

        while True:
            if self.recalibrateFlag.get():
                await self.recalibrate(gyro)
            startTime = time.time()
            angularVelReading = await gyro.get_angular_velocity()
            timeChange = time.time() - startTime
            self.totalAngleChangeX += (angularVelReading.x - self.averageDriftX) * timeChange
            self.totalAngleChangeY += (angularVelReading.y - self.averageDriftY) * timeChange
            self.totalAngleChangeZ += (angularVelReading.z - self.averageDriftZ) * timeChange
            #print(totalAngleChange)
            self.dataPub.set([self.totalAngleChangeX, self.totalAngleChangeY, self.totalAngleChangeZ])

        await robot.close()
        ntcore.NetworkTableInstance.destroy(inst)

if __name__ == '__main__':
    piCodeSelf = PiCode()
    asyncio.run(piCodeSelf.main())
