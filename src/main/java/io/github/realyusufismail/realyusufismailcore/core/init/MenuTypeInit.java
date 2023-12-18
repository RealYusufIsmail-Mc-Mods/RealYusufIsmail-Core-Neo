/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.realyusufismailcore.core.init;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.blocks.LegacySmithingMenu;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class MenuTypeInit {
    public static DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RealYusufIsmailCore.MOD_ID);

    public static final RegistryObject<MenuType<LegacySmithingMenu>> LEGACY_SMITHING_TABLE =
            register("legacy_smithing_table", LegacySmithingMenu::new,
                    FeatureFlags.REGISTRY.allFlags());


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(
            String name, MenuType.MenuSupplier<T> supplier, @Nullable FeatureFlagSet flagSet) {
        FeatureFlagSet finalFlagSet =
                Objects.requireNonNullElseGet(flagSet, FeatureFlags.REGISTRY::allFlags);
        return MENU_TYPES.register(name, () -> new MenuType<>(supplier, finalFlagSet));
    }
}
