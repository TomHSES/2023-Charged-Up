package frc.robot.Utils;

public class MathUtils 
{
    public static Double Clamp(Double value, Double min, Double max)
    {
        Double returnedValue = value;
        if (value < min)
        {
            returnedValue = min;
        }
        
        if (value > max)
        {
            returnedValue = max;
        }
        return returnedValue;
    }
}
