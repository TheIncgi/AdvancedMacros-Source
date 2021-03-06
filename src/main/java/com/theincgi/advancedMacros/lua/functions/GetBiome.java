package com.theincgi.advancedMacros.lua.functions;

import org.luaj.vm2_v3_0_1.LuaError;
import org.luaj.vm2_v3_0_1.LuaTable;
import org.luaj.vm2_v3_0_1.LuaValue;
import org.luaj.vm2_v3_0_1.Varargs;
import org.luaj.vm2_v3_0_1.lib.VarArgFunction;

import com.theincgi.advancedMacros.AdvancedMacros;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.RainType;

public class GetBiome extends VarArgFunction{
	@Override
	public Varargs invoke(Varargs args) {
		BlockPos pos = null;
		
		if(args.narg()==0) {
			pos = AdvancedMacros.getMinecraft().player.getPosition();
		}else if(args.narg()==2){
			pos = new BlockPos(args.arg(1).checkint(),64, args.arg(2).checkint());
		}else {
			throw new LuaError("Invalid args: NONE or X, Z");
		}
		World w = AdvancedMacros.getMinecraft().player.getEntityWorld();
		LuaTable out = new LuaTable();
		Biome b = w.getBiome(pos);
		out.set(1, b.getDisplayName().getUnformattedComponentText());
		LuaTable details = new LuaTable();
		details.set("canSnow", LuaValue.valueOf(b.getPrecipitation().equals(RainType.SNOW)));
		details.set("canRain", LuaValue.valueOf(b.getPrecipitation().equals(RainType.RAIN)));
		details.set("rainfall", b.getDownfall());
		details.set("temp", b.getTempCategory().name().toLowerCase()); //cold medium warm ocean
		out.set(2, details);
		return out.unpack();
	}
}
