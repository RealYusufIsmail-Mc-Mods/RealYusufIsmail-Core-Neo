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
package io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.GeneratorBuilder;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.builder.BiomeSourceBuilder;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.builder.Reference;
import lombok.Getter;
import lombok.val;

@Getter
public class ReferenceNoiseBuilder {

    private final GeneratorBuilder generatorBuilder;

    private final Reference reference;
    private final BiomeSourceBuilder biomeSourceBuilder;

    public ReferenceNoiseBuilder(
            GeneratorBuilder generatorBuilder, Reference reference, BiomeSourceBuilder biomeSourceBuilder) {
        this.generatorBuilder = generatorBuilder;
        this.reference = reference;
        this.biomeSourceBuilder = biomeSourceBuilder;
    }

    public JsonElement toJson() {
        val object = new JsonObject();

        object.addProperty("settings", reference.getId());

        return object;
    }
}
