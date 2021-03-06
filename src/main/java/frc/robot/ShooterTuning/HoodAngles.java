package frc.robot.ShooterTuning;
import frc.lib.util.InterpolatingDouble;
import frc.lib.util.InterpolatingTreeMap;
public class HoodAngles {
	public static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> kHoodAutoAimMap = new InterpolatingTreeMap<>();
 	static {
		/*
		Thu, 5 Mar 2020 15:17:30 -0600
		Castle
		(inch, angle)
		*/
		kHoodAutoAimMap.put(new InterpolatingDouble(290.1706649763184), new InterpolatingDouble(49.972099021714286));
		kHoodAutoAimMap.put(new InterpolatingDouble(264.50085045612724), new InterpolatingDouble(63.46017094371428));
		kHoodAutoAimMap.put(new InterpolatingDouble(239.43101524386324), new InterpolatingDouble(66.19419689142858));
		kHoodAutoAimMap.put(new InterpolatingDouble(206.36752204794863), new InterpolatingDouble(66.18791899028571));
		kHoodAutoAimMap.put(new InterpolatingDouble(188.5386998220804), new InterpolatingDouble(63.68303643428571));
		kHoodAutoAimMap.put(new InterpolatingDouble(168.67186085171747), new InterpolatingDouble(65.19914956028572));
		kHoodAutoAimMap.put(new InterpolatingDouble(147.2179021566132), new InterpolatingDouble(63.65164692857143));
	
	//short range
		kHoodAutoAimMap.put(new InterpolatingDouble(125.72961226676196), new InterpolatingDouble(63.011301012000004));
		kHoodAutoAimMap.put(new InterpolatingDouble(116.48629063545005), new InterpolatingDouble(62.38978879885714));
		kHoodAutoAimMap.put(new InterpolatingDouble(99.44039127087046), new InterpolatingDouble(58.867884941142854));
		kHoodAutoAimMap.put(new InterpolatingDouble(81.58882577441452), new InterpolatingDouble(55.67243325942857));
	
	
	
	
	
	}
}
