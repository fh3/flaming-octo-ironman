/**
 * 
 */
package com.flamingOctoIronman.math;

import info.yeppp.CpuArchitecture;
import info.yeppp.Library;

/**
 * @author Quint
 *
 */
public class GameMath {
	private static final CpuArchitecture architecture = Library.getCpuArchitecture();
	
	public GameMath(CpuArchitecture architecture){
		
	}
	
	public static CpuArchitecture getArchitecture(){
		return architecture;
	}
}
