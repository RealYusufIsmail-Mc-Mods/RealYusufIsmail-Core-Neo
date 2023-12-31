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
package io.github.realyusufismail.realyusufismailcore.recipe;

import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

/**
 * Taken from
 *
 * @see SimpleCookingRecipeBuilder
 */
@SuppressWarnings("unused")
public class YusufSimpleCookingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final CookingBookCategory bookCategory;
    private final Item result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    @Nullable
    private String group;

    private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

    private YusufSimpleCookingRecipeBuilder(
            RecipeCategory category,
            CookingBookCategory bookCategory,
            @NotNull ItemLike result,
            Ingredient ingredient,
            float experience,
            int cookingTime,
            RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.result = result.asItem();
        this.ingredient = ingredient;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.serializer = serializer;
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder cooking(
            RecipeCategory category,
            Ingredient ingredient,
            ItemLike result,
            float experience,
            int cookingTime,
            RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        return new YusufSimpleCookingRecipeBuilder(
                category,
                determineRecipeCategory(serializer, result),
                result,
                ingredient,
                experience,
                cookingTime,
                serializer);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder campfireCooking(
            RecipeCategory category, Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
        return new YusufSimpleCookingRecipeBuilder(
                category,
                CookingBookCategory.FOOD,
                result,
                ingredient,
                experience,
                cookingTime,
                RecipeSerializer.CAMPFIRE_COOKING_RECIPE);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder blasting(
            RecipeCategory category, Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
        return new YusufSimpleCookingRecipeBuilder(
                category,
                determineBlastingRecipeCategory(result),
                result,
                ingredient,
                experience,
                cookingTime,
                RecipeSerializer.BLASTING_RECIPE);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder smelting(
            RecipeCategory category, Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
        return new YusufSimpleCookingRecipeBuilder(
                category,
                determineSmeltingRecipeCategory(result),
                result,
                ingredient,
                experience,
                cookingTime,
                RecipeSerializer.SMELTING_RECIPE);
    }

    public static @NotNull YusufSimpleCookingRecipeBuilder smoking(
            RecipeCategory category, Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
        return new YusufSimpleCookingRecipeBuilder(
                category,
                CookingBookCategory.FOOD,
                result,
                ingredient,
                experience,
                cookingTime,
                RecipeSerializer.SMOKING_RECIPE);
    }

    private static CookingBookCategory determineRecipeCategory(
            RecipeSerializer<? extends AbstractCookingRecipe> pSerializer, ItemLike pResult) {
        if (pSerializer == RecipeSerializer.SMELTING_RECIPE) {
            return determineSmeltingRecipeCategory(pResult);
        } else if (pSerializer == RecipeSerializer.BLASTING_RECIPE) {
            return determineBlastingRecipeCategory(pResult);
        } else if (pSerializer != RecipeSerializer.SMOKING_RECIPE
                && pSerializer != RecipeSerializer.CAMPFIRE_COOKING_RECIPE) {
            throw new IllegalStateException("Unknown cooking recipe type");
        } else {
            return CookingBookCategory.FOOD;
        }
    }

    private static CookingBookCategory determineSmeltingRecipeCategory(ItemLike result) {
        if (result.asItem().isEdible()) {
            return CookingBookCategory.FOOD;
        } else {
            return result.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
        }
    }

    private static CookingBookCategory determineBlastingRecipeCategory(ItemLike result) {
        return result.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
    }

    public @NotNull YusufSimpleCookingRecipeBuilder unlockedBy(
            @NotNull String creterionId, @NotNull Criterion<?> criterion) {
        this.criteria.put(creterionId, criterion);
        return this;
    }

    public @NotNull YusufSimpleCookingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public @NotNull Item getResult() {
        return this.result;
    }

    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);

        Advancement.Builder advancementBuilder = recipeOutput
                .advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);

        recipeOutput.accept(new Result(
                resourceLocation,
                this.group == null ? "" : this.group,
                this.bookCategory,
                this.ingredient,
                this.result,
                this.experience,
                this.cookingTime,
                advancementBuilder.build(resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")),
                this.serializer));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(
            ResourceLocation id,
            String group,
            CookingBookCategory bookCategory,
            Ingredient ingredient,
            Item result,
            float experience,
            int cookingTime,
            AdvancementHolder advancement,
            RecipeSerializer<? extends AbstractCookingRecipe> serializer)
            implements FinishedRecipe {

        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }

            jsonObject.add("ingredient", this.ingredient.toJson(false));
            jsonObject.addProperty(
                    "result",
                    Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(this.result), "Item is null")
                            .toString());
            jsonObject.addProperty("experience", this.experience);
            jsonObject.addProperty("cookingtime", this.cookingTime);
        }

        public @NotNull RecipeSerializer<?> type() {
            return this.serializer;
        }

        public @NotNull JsonObject serializeAdvancement() {
            return this.advancement.value().serializeToJson();
        }
    }
}
