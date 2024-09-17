package com.hbm.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialSolid extends Material {

	public MaterialSolid(){
		super(MapColor.STONE);
		this.setImmovableMobility();
	}
	
	public boolean isSolid() {
		return true;
	}

}
