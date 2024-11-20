package com.hbm.handler.jei;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.CentrifugeRecipes;
import com.hbm.inventory.CrystallizerRecipes;
import com.hbm.inventory.ShredderRecipes;
import com.hbm.inventory.RBMKOutgasserRecipes;
import com.hbm.inventory.DFCRecipes;
import com.hbm.inventory.CrackRecipes;
import com.hbm.inventory.gui.GUIAnvil;
import com.hbm.inventory.gui.GUIBook;
import com.hbm.inventory.gui.GUICrystallizer;
import com.hbm.inventory.gui.GUIHadron;
import com.hbm.inventory.gui.GUIMixer;
import com.hbm.inventory.gui.GUIMachineAssembler;
import com.hbm.inventory.gui.GUIMachineBoiler;
import com.hbm.inventory.gui.GUIMachineBoilerElectric;
import com.hbm.inventory.gui.GUIMachineBoilerRTG;
import com.hbm.inventory.gui.GUIMachineCMBFactory;
import com.hbm.inventory.gui.GUIMachineCentrifuge;
import com.hbm.inventory.gui.GUIMachineChemplant;
import com.hbm.inventory.gui.GUIMachineCyclotron;
import com.hbm.inventory.gui.GUIMachineEPress;
import com.hbm.inventory.gui.GUIMachineGasCent;
import com.hbm.inventory.gui.GUIMachinePress;
import com.hbm.inventory.gui.GUIMachineReactor;
import com.hbm.inventory.gui.GUIMachineRefinery;
import com.hbm.inventory.gui.GUIMachineShredder;
import com.hbm.inventory.gui.GUISILEX;
import com.hbm.inventory.gui.GUITestDiFurnace;
import com.hbm.inventory.gui.GUIDiFurnaceRTG;
import com.hbm.inventory.gui.GUIRBMKOutgasser;
import com.hbm.inventory.gui.GUIFWatzCore;
import com.hbm.inventory.gui.GUIMachineSchrabidiumTransmutator;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.main.MainRegistry;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

@JEIPlugin
public class JEIConfig implements IModPlugin {

	public static final String ASSEMBLY = "hbm.assembly";
	public static final String CHEMPLANT = "hbm.chemplant";
	public static final String MIXER = "hbm.mixer";
	public static final String CYCLOTRON = "hbm.cyclotron";
	public static final String PRESS = "hbm.press";
	public static final String ALLOY = "hbm.alloy";
	public static final String BOILER = "hbm.boiler";
	public static final String CENTRIFUGE = "hbm.centrifuge";
	public static final String CMB = "hbm.cmb_furnace";
	public static final String GAS_CENT = "hbm.gas_centrifuge";
	public static final String REACTOR = "hbm.reactor";
	public static final String REFINERY = "hbm.refinery";
	public static final String CRACKING = "hbm.cracking";
	public static final String FRACTIONING = "hbm.fracturing";
	public static final String SHREDDER = "hbm.shredder";
	public static final String FLUIDS = "hbm.fluids";
	public static final String CRYSTALLIZER = "hbm.crystallizer";
	public static final String BOOK = "hbm.book_of";
	public static final String FUSION_BYPRODUCT = "hbm.fusionbyproduct";
	public static final String HADRON = "hbm.hadron";
	public static final String SILEX = "hbm.silex";
	public static final String SILEX_RADIO = "hbm.silexradio";
	public static final String SILEX_MICRO = "hbm.silexmicro";
	public static final String SILEX_IR = "hbm.silexir";
	public static final String SILEX_VISIBLE = "hbm.silexvisible";
	public static final String SILEX_UV = "hbm.silexuv";
	public static final String SILEX_XRAY = "hbm.silexray";
	public static final String SILEX_GAMMA = "hbm.silexgamma";
	public static final String SILEX_DIGAMMA = "hbm.silexdigamma";
	public static final String WASTEDRUM = "hbm.waste_drum";
	public static final String STORAGEDRUM = "hbm.storage_drum";
	public static final String SMITHING = "hbm.smithing";
	public static final String ANVIL = "hbm.anvil";
	public static final String RBMKOUTGASSER = "hbm.rbmk_outgasser";
	public static final String RBMKFUEL = "hbm.rbmkfueluncrafting";
	public static final String SAFE_REACTOR = "hbm.safe_reactor";
	public static final String DFC = "hbm.dfc";
	public static final String TRANSMUTATION = "hbm.transmutation";

	@Override
	public void register(IModRegistry registry) {
		if(!GeneralConfig.jei)
			return;
		registry.addRecipeRegistryPlugin(new HbmJeiRegistryPlugin());

		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_assembler), ASSEMBLY);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_chemplant), CHEMPLANT);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_mixer), MIXER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_cyclotron), CYCLOTRON);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_schrabidium_transmutator), TRANSMUTATION);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_press), PRESS);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_epress), PRESS);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_difurnace_off), ALLOY);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_difurnace_rtg_off), ALLOY);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_boiler_off), BOILER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_boiler_electric_off), BOILER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_boiler_rtg_off), BOILER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_solar_boiler), BOILER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.heat_boiler), BOILER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.rbmk_heater), BOILER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_centrifuge), CENTRIFUGE);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_combine_factory), CMB);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_gascent), GAS_CENT);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_reactor), REACTOR);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_waste_drum), WASTEDRUM);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_storage_drum), STORAGEDRUM);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_refinery), REFINERY);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_catalytic_cracker), CRACKING);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_fraction_tower), FRACTIONING);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_shredder), SHREDDER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_fluidtank), FLUIDS);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_crystallizer), CRYSTALLIZER);
		//This recipe catalyst doesn't work, since the book of is blacklisted.
		registry.addRecipeCatalyst(new ItemStack(ModItems.book_of_), BOOK);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.iter), FUSION_BYPRODUCT);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.hadron_core), HADRON);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.machine_silex), SILEX);
		registry.addRecipeCatalyst(new ItemStack(ModItems.laser_crystal_nano), SILEX_RADIO);
		registry.addRecipeCatalyst(new ItemStack(ModItems.laser_crystal_pentacene), SILEX_MICRO);
		registry.addRecipeCatalyst(new ItemStack(ModItems.laser_crystal_co2), SILEX_IR);
		registry.addRecipeCatalyst(new ItemStack(ModItems.laser_crystal_bismuth), SILEX_VISIBLE);
		registry.addRecipeCatalyst(new ItemStack(ModItems.laser_crystal_cmb), SILEX_UV);
		registry.addRecipeCatalyst(new ItemStack(ModItems.laser_crystal_dem), SILEX_XRAY);
		registry.addRecipeCatalyst(new ItemStack(ModItems.laser_crystal_bale), SILEX_GAMMA);
		registry.addRecipeCatalyst(new ItemStack(ModItems.laser_crystal_digamma), SILEX_DIGAMMA);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.anvil_iron), SMITHING);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.anvil_steel), ANVIL);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.rbmk_outgasser), RBMKOUTGASSER);
		registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), RBMKFUEL);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.fwatz_core), SAFE_REACTOR);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.crate_tungsten), DFC);
		
		// registry.addRecipes(ItemAssemblyTemplate.recipes, ASSEMBLY);
		registry.addRecipes(JeiRecipes.getChemistryRecipes(), CHEMPLANT);
		registry.addRecipes(JeiRecipes.getMixerRecipes(), MIXER);
		registry.addRecipes(JeiRecipes.getCyclotronRecipes(), CYCLOTRON);
		registry.addRecipes(JeiRecipes.getTransmutationRecipes(), TRANSMUTATION);
		registry.addRecipes(JeiRecipes.getPressRecipes(), PRESS);
		registry.addRecipes(JeiRecipes.getAlloyRecipes(), ALLOY);
		registry.addRecipes(JeiRecipes.getBoilerRecipes(), BOILER);
		registry.addRecipes(CentrifugeRecipes.getCentrifugeRecipes(), CENTRIFUGE);
		registry.addRecipes(JeiRecipes.getCMBRecipes(), CMB);
		registry.addRecipes(JeiRecipes.getGasCentrifugeRecipes(), GAS_CENT);
		registry.addRecipes(JeiRecipes.getReactorRecipes(), REACTOR);
		registry.addRecipes(JeiRecipes.getWasteDrumRecipes(), WASTEDRUM);
		registry.addRecipes(JeiRecipes.getStorageDrumRecipes(), STORAGEDRUM);
		registry.addRecipes(JeiRecipes.getRefineryRecipe(), REFINERY);
		registry.addRecipes(JeiRecipes.getCrackingRecipe(), CRACKING);
		registry.addRecipes(JeiRecipes.getFractioningRecipe(), FRACTIONING);
		registry.addRecipes(ShredderRecipes.getShredderRecipes(), SHREDDER);
		registry.addRecipes(JeiRecipes.getFluidEquivalences(), FLUIDS);
		registry.addRecipes(CrystallizerRecipes.getRecipes(), CRYSTALLIZER);
		registry.addRecipes(JeiRecipes.getBookRecipes(), BOOK);
		registry.addRecipes(JeiRecipes.getFusionByproducts(), FUSION_BYPRODUCT);
		registry.addRecipes(JeiRecipes.getHadronRecipes(), HADRON);
		registry.addRecipes(JeiRecipes.getSILEXRecipes(), SILEX);
		registry.addRecipes(JeiRecipes.getSILEXRecipes(EnumWavelengths.RADIO), SILEX_RADIO);
		registry.addRecipes(JeiRecipes.getSILEXRecipes(EnumWavelengths.MICRO), SILEX_MICRO);
		registry.addRecipes(JeiRecipes.getSILEXRecipes(EnumWavelengths.IR), SILEX_IR);
		registry.addRecipes(JeiRecipes.getSILEXRecipes(EnumWavelengths.VISIBLE), SILEX_VISIBLE);
		registry.addRecipes(JeiRecipes.getSILEXRecipes(EnumWavelengths.UV), SILEX_UV);
		registry.addRecipes(JeiRecipes.getSILEXRecipes(EnumWavelengths.XRAY), SILEX_XRAY);
		registry.addRecipes(JeiRecipes.getSILEXRecipes(EnumWavelengths.GAMMA), SILEX_GAMMA);
		registry.addRecipes(JeiRecipes.getSILEXRecipes(EnumWavelengths.DRX), SILEX_DIGAMMA);
		registry.addRecipes(JeiRecipes.getSmithingRecipes(), SMITHING);
		registry.addRecipes(JeiRecipes.getAnvilRecipes(), ANVIL);
		registry.addRecipes(RBMKOutgasserRecipes.getRBMKOutgasserRecipes(), RBMKOUTGASSER);
		registry.addRecipes(JeiRecipes.getRBMKFuelRecipes(), RBMKFUEL);
		registry.addRecipes(JeiRecipes.getSAFERecipes(), SAFE_REACTOR);
		registry.addRecipes(DFCRecipes.getDFCRecipes(), DFC);

		registry.addRecipeClickArea(GUIMachineAssembler.class, 45, 83, 82, 30, ASSEMBLY);
		registry.addRecipeClickArea(GUIMachineChemplant.class, 45, 90, 85, 15, CHEMPLANT);
		registry.addRecipeClickArea(GUIMixer.class, 62, 36, 52, 44, MIXER);
		registry.addRecipeClickArea(GUIMachineCyclotron.class, 50, 24, 40, 40, CYCLOTRON);
		registry.addRecipeClickArea(GUIMachinePress.class, 80, 35, 15, 15, PRESS);
		registry.addRecipeClickArea(GUIMachineEPress.class, 80, 35, 15, 15, PRESS);
		registry.addRecipeClickArea(GUITestDiFurnace.class, 102, 36, 21, 14, ALLOY);
		registry.addRecipeClickArea(GUIDiFurnaceRTG.class, 102, 36, 21, 14, ALLOY);
		registry.addRecipeClickArea(GUIMachineBoiler.class, 61, 34, 17, 35, BOILER);
		registry.addRecipeClickArea(GUIMachineBoilerElectric.class, 61, 34, 17, 35, BOILER);
		registry.addRecipeClickArea(GUIMachineBoilerRTG.class, 61, 34, 17, 17, BOILER);
		registry.addRecipeClickArea(GUIMachineCentrifuge.class, 35, 9, 106, 40, CENTRIFUGE);
		registry.addRecipeClickArea(GUIMachineCMBFactory.class, 111, 35, 21, 14, CMB);
		registry.addRecipeClickArea(GUIMachineGasCent.class, 118, 36, 51, 13, GAS_CENT);
		registry.addRecipeClickArea(GUIMachineReactor.class, 80, 35, 21, 14, REACTOR);
		registry.addRecipeClickArea(GUIMachineRefinery.class, 79, 71, 71, 17, REFINERY);
		registry.addRecipeClickArea(GUIMachineShredder.class, 43, 89, 53, 17, SHREDDER);
		registry.addRecipeClickArea(GUICrystallizer.class, 79, 40, 29, 26, CRYSTALLIZER);
		registry.addRecipeClickArea(GUIBook.class, 89, 34, 23, 16, BOOK);
		registry.addRecipeClickArea(GUIHadron.class, 71, 28, 32, 32, HADRON);
		registry.addRecipeClickArea(GUISILEX.class, 45, 82, 113-45, 125-82, SILEX);
		registry.addRecipeClickArea(GUIAnvil.class, 34, 26, 52-34, 44-26, SMITHING);
		registry.addRecipeClickArea(GUIAnvil.class, 12, 50, 48-12, 66-50, ANVIL);
		registry.addRecipeClickArea(GUIFWatzCore.class, 52, 64, 72, 19, SAFE_REACTOR);
		registry.addRecipeClickArea(GUIRBMKOutgasser.class, 64, 53, 48, 16, RBMKOUTGASSER);
		registry.addRecipeClickArea(GUIMachineSchrabidiumTransmutator.class, 64, 56, 66, 31, TRANSMUTATION);

		IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();

		// Some things are even beyond my control...or are they?
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.memory));

		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ajr_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ajr_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ajr_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ajr_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ajro_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ajro_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ajro_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ajro_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.alloy_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.alloy_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.alloy_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.alloy_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.apple_euphemium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.apple_schrabidium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.apple_schrabidium1));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.apple_schrabidium2));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.armor_polish));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.australium_iii));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.back_tesla));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bathwater));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bathwater_mk2));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bathwater_mk3));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.battery_creative));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bj_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bj_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bj_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bj_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bj_plate_jetpack));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.black_diamond));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bobmazon_hidden));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bomb_caller));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.bomb_multi));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.bomb_waffle));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.canned_bhole));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.canned_mystery));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.canteen_fab));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.canteen_vodka));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.cc_plasma_gun));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.chocolate_milk));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.cmb_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.cmb_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.cmb_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.cmb_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.cobalt_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.cobalt_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.cobalt_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.cobalt_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.coin_creeper));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.coin_maskman));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.coin_radiation));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.coin_ufo));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.coin_worm));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.crashed_balefire));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.crate_caller));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.crucible));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.desh_axe));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.desh_hoe));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.desh_pickaxe));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.desh_shovel));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.desh_sword));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.det_bale));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.det_n2));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.det_nuke));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.detonator_laser));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.dns_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.dns_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.dns_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.dns_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.euphemium_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.euphemium_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.euphemium_kit));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.euphemium_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.euphemium_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.euphemium_stopper));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.fabsols_vodka));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.fau_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.fau_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.fau_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.fau_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.flame_war));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.float_bomb));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_black_hole));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_breach));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_burst));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_if_hopwire));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_if_mystery));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_if_null));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_if_spark));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_nuclear));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_nuke));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_pulse));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_schrabidium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_solinium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.grenade_zomg));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_ar15));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_avenger));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_b92));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_b93));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_bf));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_bolt_action));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_bolt_action_green));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_bolt_action_saturnite));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_bolter));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_brimstone));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_calamity));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_calamity_dual));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_cryolator));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_dampfmaschine));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_darter));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_deagle));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_defabricator));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_egon));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_emp));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_euthanasia));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_fatman));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_flamer));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_flechette));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_folly));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_hk69));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_hp));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_immolator));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_jack));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_jshotgun));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_karl));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_ks23));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_lacunae));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_lever_action));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_lever_action_dark));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_lever_action_sonata));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_minigun));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_mirv));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_moist_nugget));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_mp));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_mp40));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_osipr));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_panzerschreck));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_proto));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_quadro));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_blackjack));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_cursed));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_gold));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_iron));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_lead));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_nightmare));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_nightmare2));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_nopip));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_pip));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_red));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_saturnite));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_schrabidium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_revolver_silver));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_rpg));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_sauer));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_skystinger));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_spark));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_stinger));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_super_shotgun));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_supershotgun));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_thompson));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_uboinik));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_uzi));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_uzi_saturnite));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_uzi_saturnite_silencer));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_uzi_silencer));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_vortex));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_xvl1456));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.gun_zomg));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.heart_booster));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.heart_container));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.heart_fab));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.heart_piece));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.hev_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.hev_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.hev_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.hev_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.hf_sword));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.hs_sword));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ingot_euphemium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.injector_5htp));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.injector_knife));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ink));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.jetpack_boost));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.jetpack_break));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.jetpack_fly));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.jetpack_glider));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.jetpack_vector));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mese_gavel));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteor_charm));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteor_remote));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_demonic));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_hardened));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_reforged));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_baleful));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_bred));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_etched));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_fused));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_irradiated));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_machined));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_seared));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_treated));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.meteorite_sword_warped));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.missile_bhole));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.missile_carrier));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.missile_doomsday));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.missile_endo));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.missile_exo));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.missile_micro));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.missile_schrabidium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.missile_taint));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.missile_volcano));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_10_cloud));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_10_nuclear));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_10_nuclear_large));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_10_taint));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_15_boxcar));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_15_balefire));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_15_mirv));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_15_nuclear_shark));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_15_thermo));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.mp_warhead_15_volcano));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.multitool_beam));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.multitool_decon));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.multitool_ext));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.multitool_hit));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.multitool_joule));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.multitool_mega));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.multitool_miner));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.multitool_silk));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.multitool_sky));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.hat));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.nugget_euphemium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_custom));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_fleija));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_fstbmb));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_gadget));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_man));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_mike));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_n2));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_prototype));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_solinium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.nuke_tsar));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.paa_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.paa_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.paa_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.paa_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.pocket_ptsd));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.protection_charm));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.rod_euphemium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.rod_quad_euphemium));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.rpa_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.rpa_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.rpa_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.rpa_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.sat_resonator));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_axe));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_hammer));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_hoe));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_pickaxe));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_shovel));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.schrabidium_sword));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.scrumpy));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.security_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.security_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.security_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.security_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.servo_set));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.servo_set_desh));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.shackles));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.shimmer_axe));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.shimmer_sledge));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.starmetal_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.starmetal_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.starmetal_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.starmetal_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.steel_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.steel_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.steel_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.steel_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.syringe_awesome));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.syringe_metal_medx));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.syringe_metal_psycho));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.t45_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.t45_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.t45_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.t45_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.therm_endo));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.therm_exo));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.titanium_boots));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.titanium_helmet));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.titanium_legs));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.titanium_plate));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.turret_cwis));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.turret_spitfire));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.v1));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.watch));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.wd40));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.wild_p));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.wings_limp));
		blacklist.addIngredientToBlacklist(new ItemStack(ModItems.wings_murk));
		if(MainRegistry.polaroidID != 11) {
			blacklist.addIngredientToBlacklist(new ItemStack(ModItems.book_secret));
			blacklist.addIngredientToBlacklist(new ItemStack(ModItems.book_of_));
			blacklist.addIngredientToBlacklist(new ItemStack(ModItems.ams_core_thingy));
		}
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_assembler));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_centrifuge));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_chemplant));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_cyclotron));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_flare));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_fluidtank));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_pumpjack));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_refinery));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_turbofan));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_well));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_ams_base));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_ams_emitter));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_ams_limiter));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_reactor_small));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_assembler));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_chemplant));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_cyclotron));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_flare));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_fluidtank));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_pumpjack));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_refinery));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_turbofan));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_well));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_ams_base));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_ams_emitter));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_ams_limiter));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_port_reactor_small));
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.dummy_block_silo_hatch));
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		if(!GeneralConfig.jei)
			return;
		IGuiHelper help = registry.getJeiHelpers().getGuiHelper();
		registry.addRecipeCategories(new AnvilRecipeHandler(help),
				new SmithingRecipeHandler(help),
				new PressRecipeHandler(help),
				new AlloyFurnaceRecipeHandler(help),
				new ShredderRecipeHandler(help),
				new AssemblerRecipeHandler(help),
				new ChemplantRecipeHandler(help),
				new MixerRecipeHandler(help),
				new BoilerRecipeHandler(help),
				new RefineryRecipeHandler(help),
				new CrackingRecipeHandler(help),
				new FractioningRecipeHandler(help),
				new CrystallizerRecipeHandler(help),
				new CentrifugeRecipeHandler(help),
				new GasCentrifugeRecipeHandler(help),
				new CyclotronRecipeHandler(help),
				new TransmutationRecipeHandler(help),
				new CMBFurnaceRecipeHandler(help),
				new ReactorRecipeHandler(help),
				new WasteDrumRecipeHandler(help),
				new StorageDrumRecipeHandler(help),
				new FluidRecipeHandler(help),
				new SILEXRecipeHandler(help),
				new SILEXRadioRecipeHandler(help),
				new SILEXMicroRecipeHandler(help),
				new SILEXIrRecipeHandler(help),
				new SILEXVisibleRecipeHandler(help),
				new SILEXUVRecipeHandler(help),
				new SILEXXRayRecipeHandler(help),
				new SILEXGammaRecipeHandler(help),
				new SILEXDigammaRecipeHandler(help),
				new RBMKOutgasserRecipeHandler(help),
				new RBMKFuelRecipeHandler(help),
				new FusionRecipeHandler(help),
				new HadronRecipeHandler(help),
				new SAFERecipeHandler(help),
				new DFCRecipeHandler(help),
				new BookRecipeHandler(help));
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		if(!GeneralConfig.jei)
			return;
		subtypeRegistry.registerSubtypeInterpreter(ModItems.cell, (ItemStack stack) -> {
			FluidStack fluid = FluidUtil.getFluidContained(stack);
			return ModItems.cell.getUnlocalizedName() + (fluid == null ? "empty" : fluid.getFluid().getUnlocalizedName() + fluid.amount);
		});
		subtypeRegistry.registerSubtypeInterpreter(ModItems.fluid_barrel_full, (ItemStack stack) -> {
			FluidStack fluid = FluidUtil.getFluidContained(stack);
			return ModItems.fluid_barrel_full.getUnlocalizedName() + (fluid == null ? "empty" : fluid.getFluid().getUnlocalizedName() + fluid.amount);
		});
		subtypeRegistry.registerSubtypeInterpreter(ModItems.fluid_tank_full, (ItemStack stack) -> {
			FluidStack fluid = FluidUtil.getFluidContained(stack);
			return ModItems.fluid_tank_full.getUnlocalizedName() + (fluid == null ? "empty" : fluid.getFluid().getUnlocalizedName() + fluid.amount);
		});
		subtypeRegistry.registerSubtypeInterpreter(ModItems.canister_generic, (ItemStack stack) -> {
			FluidStack fluid = FluidUtil.getFluidContained(stack);
			return ModItems.canister_generic.getUnlocalizedName() + (fluid == null ? "empty" : fluid.getFluid().getUnlocalizedName() + fluid.amount);
		});
		subtypeRegistry.registerSubtypeInterpreter(ModItems.missile_custom, (ItemStack stack) -> {
			return ModItems.missile_custom.getUnlocalizedName() + "w" + Integer.toString(ItemCustomMissile.readFromNBT(stack, "warhead")) + "f" + Integer.toString(ItemCustomMissile.readFromNBT(stack, "fuselage")) + "s" + Integer.toString(ItemCustomMissile.readFromNBT(stack, "stability")) + "t" + Integer.toString(ItemCustomMissile.readFromNBT(stack, "thruster"));
		});
		subtypeRegistry.registerSubtypeInterpreter(ModItems.fluid_icon, (ItemStack stack) -> {
			if(stack.hasTagCompound()) {
				String s = "";
				if(stack.getTagCompound().hasKey("type"))
					s = s + stack.getTagCompound().getString("type");
				return s;
			}
			return "";
		});
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}
}
