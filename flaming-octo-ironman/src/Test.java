import info.yeppp.CpuArchitecture;
import info.yeppp.CpuSimdFeature;
import info.yeppp.Library;

import java.util.Iterator;

import com.flamingOctoIronman.math.matrix.Matrix;
import com.flamingOctoIronman.math.matrix.MatrixMath;

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
		/*Vector a = new Vector(5, 1, 2, true);
		Vector b = new Vector(2, 5, 6, true);
		Vector result = VectorCalculations.vectorSubtraction(a, b);
		System.out.println(result.toString());
		*/
		
		//Testing Matrix stuff
		float[][] matrixAArray = {{16, 15, 14, 13}, {12, 11, 10, 9}, {8, 7, 6, 5}, {4, 3, 2, 1}};
		Matrix b = new Matrix(matrixAArray);
		//float[][] matrixBArray = {{5, 4, 3, 2}};
		//Matrix a = new Matrix(matrixBArray);
		//float[] column = {1, 2, 3, 4};
		//m.setMatrixColumn(1, column);
		//m.scalarMultiplication(5);
		//Matrix p = MatrixMath.multipyMatrices(a, b);
		b.gaussianElimination();
		System.out.println(b.toString());
	}
}