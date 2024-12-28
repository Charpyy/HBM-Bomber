package com.hbm.inventory;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.HashSet;

import com.hbm.config.VersatileConfig;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StorageDrumRecipes {

	public static LinkedHashMap<ComparableStack, ItemStack> recipeOutputs = new LinkedHashMap<>();
	public static HashSet<ItemStack> outputs = new HashSet<>();
	public static HashMap<ComparableStack, int[]> recipeWastes = new HashMap<>();
	
	public static void registerRecipes() {

		//input, output

		for(int i = 0; i < ItemWasteLong.WasteClass.values().length; i++){
			ItemWasteLong.WasteClass waste = ItemWasteLong.WasteClass.values()[i];
			addRecipe(new ComparableStack(ModItems.nuclear_waste_long, 1, i), 
				new ItemStack(ModItems.nuclear_waste_long_depleted, 1, i), 
				VersatileConfig.getLongDecayChance(), 
				waste.liquid, 
				waste.gas
			);
			addRecipe(new ComparableStack(ModItems.nuclear_waste_long_tiny, 1, i), 
				new ItemStack(ModItems.nuclear_waste_long_depleted_tiny, 1, i), 
				(int)(VersatileConfig.getLongDecayChance()*0.1), 
				(int)(waste.liquid*0.1), 
				(int)(waste.gas*0.1)
			);
		}

		for(int i = 0; i < ItemWasteShort.WasteClass.values().length; i++){
			ItemWasteShort.WasteClass waste = ItemWasteShort.WasteClass.values()[i];
			addRecipe(new ComparableStack(ModItems.nuclear_waste_short, 1, i), 
				new ItemStack(ModItems.nuclear_waste_short_depleted, 1, i), 
				VersatileConfig.getShortDecayChance(), 
				waste.liquid, 
				waste.gas
			);
			addRecipe(new ComparableStack(ModItems.nuclear_waste_short_tiny, 1, i), 
				new ItemStack(ModItems.nuclear_waste_short_depleted_tiny, 1, i), 
				(int)(VersatileConfig.getShortDecayChance()*0.1), 
				(int)(waste.liquid*0.1), 
				(int)(waste.gas*0.1)
			);
		}

		addRecipe(new ComparableStack(ModItems.ingot_au198, 1), new ItemStack(ModItems.bottle_mercury, 1), (int)(VersatileConfig.getShortDecayChance()*0.01), 0, 0);
		addRecipe(new ComparableStack(ModItems.nugget_au198, 1), new ItemStack(ModItems.nugget_mercury, 1), (int)(VersatileConfig.getShortDecayChance()*0.001), 0, 0);
		addRecipe(new ComparableStack(ModItems.ingot_neptunium, 1), new ItemStack(ModItems.ingot_bismuth, 1), (int)(VersatileConfig.getLongDecayChance()*10), 0, 500);
		addRecipe(new ComparableStack(ModItems.nugget_neptunium, 1), new ItemStack(ModItems.nugget_bismuth, 1), (int)(VersatileConfig.getLongDecayChance()*1), 0, 50);
		addRecipe(new ComparableStack(ModItems.ingot_am241, 1), new ItemStack(ModItems.ingot_neptunium, 1), (int)(VersatileConfig.getShortDecayChance()*20), 0, 0);
		addRecipe(new ComparableStack(ModItems.nugget_am241, 1), new ItemStack(ModItems.nugget_neptunium, 1), (int)(VersatileConfig.getShortDecayChance()*2), 0, 0);
		addRecipe(new ComparableStack(ModItems.ingot_pu241, 1), new ItemStack(ModItems.ingot_am241, 1), (int)(VersatileConfig.getShortDecayChance()*0.5), 0, 0);
		addRecipe(new ComparableStack(ModItems.nugget_pu241, 1), new ItemStack(ModItems.nugget_am241, 1), (int)(VersatileConfig.getShortDecayChance()*0.05), 0, 0);
		addRecipe(new ComparableStack(ModItems.ingot_am242, 1), new ItemStack(ModItems.ingot_pu238, 1), (int)(VersatileConfig.getShortDecayChance()*10), 0, 0);
		addRecipe(new ComparableStack(ModItems.nugget_am242, 1), new ItemStack(ModItems.nugget_pu238, 1), (int)(VersatileConfig.getShortDecayChance()*1), 0, 0);
		addRecipe(new ComparableStack(ModItems.ingot_pu238, 1), new ItemStack(ModItems.ingot_lead, 1), (int)(VersatileConfig.getLongDecayChance()*3), 0, 500);
		addRecipe(new ComparableStack(ModItems.nugget_pu238, 1), new ItemStack(ModItems.nugget_lead, 1), (int)(VersatileConfig.getLongDecayChance()*0.3), 0, 50);
		addRecipe(new ComparableStack(ModItems.ingot_pu239, 1), new ItemStack(ModItems.ingot_u235, 1), (int)(VersatileConfig.getLongDecayChance()*1), 0, 500);
		addRecipe(new ComparableStack(ModItems.nugget_pu239, 1), new ItemStack(ModItems.nugget_u235, 1), (int)(VersatileConfig.getLongDecayChance()*0.1), 0, 50);
		addRecipe(new ComparableStack(ModItems.ingot_pu240, 1), new ItemStack(ModItems.ingot_th232, 1), (int)(VersatileConfig.getLongDecayChance()*30), 0, 500);
		addRecipe(new ComparableStack(ModItems.nugget_pu240, 1), new ItemStack(ModItems.nugget_th232, 1), (int)(VersatileConfig.getLongDecayChance()*3), 0, 50);
	}

	public static void addRecipe(ComparableStack input, ItemStack output, int chance, int wasteLiquid, int wasteGas){
		recipeOutputs.put(input, output);
		recipeWastes.put(input, new int[]{chance, wasteLiquid, wasteGas});
		outputs.add(output);
	}
	
	public static ItemStack getOutput(ItemStack stack) {
		if(stack == null)
			return null;
		return recipeOutputs.get(new ComparableStack(stack));
	}

	public static int[] getWaste(ItemStack stack) {
		if(stack == null)
			return null;
		return recipeWastes.get(new ComparableStack(stack));
	}

	public static boolean isOutputItem(ItemStack stack){
		return outputs.contains(new ComparableStack(stack));
	}
}
