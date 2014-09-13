import info.yeppp.CpuArchitecture;
import info.yeppp.CpuSimdFeature;
import info.yeppp.Library;

import java.util.Iterator;

import com.flamingOctoIronman.math.vector.Vector;
import com.flamingOctoIronman.math.vector.VectorCalculations;

public class Test {
	public static void main(String args[]){
		System.out.println("Test 2");
		System.out.println("Commit from austin");
		System.out.println("Commit from Oliver");
		
		
		//SIMD testing stuff
		final CpuArchitecture architecture = Library.getCpuArchitecture();
		final Iterator<CpuSimdFeature> simdFeaturesIterator = architecture.iterateSimdFeatures();
		if (simdFeaturesIterator.hasNext()) {
			System.out.println("CPU SIMD extensions:");
			while (simdFeaturesIterator.hasNext()) {
				final info.yeppp.CpuSimdFeature simdFeature = simdFeaturesIterator.next();
				System.out.println(String.format("\t%-60s\t%s", simdFeature.getDescription() + ":", (info.yeppp.Library.isSupported(simdFeature) ? "Yes" : "No")));
			}
		}
		
		//Testing vector dot product using SIMD instruction
		Vector a = new Vector(5, 1, 2, true);
		Vector b = new Vector(2, 5, 6, true);
		Vector result = VectorCalculations.vectorSubtraction(a, b);
		System.out.println(result.toString());
	}
}