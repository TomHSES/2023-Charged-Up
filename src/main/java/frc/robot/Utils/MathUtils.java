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

    public static double Sign(double value)
    {
        return value == 0.0 ? 0 : (value > 0.0 ? 1.0 : -1.0);
    }
}
