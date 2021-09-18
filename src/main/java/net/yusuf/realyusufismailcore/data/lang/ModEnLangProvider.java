/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.yusuf.realyusufismailcore.data.lang;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.FishingLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.yusuf.realyusufismailcore.RealYusufIsmailCore;
import net.yusuf.realyusufismailcore.core.init.BlockInitCore;
import net.yusuf.realyusufismailcore.core.init.ItemInitCore;
import net.yusuf.realyusufismailcore.core.itemgroup.MainItemGroup;

public class ModEnLangProvider extends LanguageProvider {

    public ModEnLangProvider(DataGenerator gen) {
        super(gen, RealYusufIsmailCore.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //block
        block(BlockInitCore.COPPER_BLOCK, "Copper Block");
        //ores
        block(BlockInitCore.COPPER_ORE, "Copper Ore");
        //ingots
        item(ItemInitCore.COPPER, "Copper");

        //others
        add(MainItemGroup.MAIN.getDisplayName().getString(), "RealYusufIsmail Core Item Group");
    }

    private <T extends Item> void item(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }

    private <T extends Block> void block(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }
}
