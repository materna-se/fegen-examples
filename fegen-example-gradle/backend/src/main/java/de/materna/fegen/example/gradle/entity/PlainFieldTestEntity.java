/**
 * Copyright 2020 Materna Information & Communications SE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.materna.fegen.example.gradle.entity;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * This entity's purpose is to test whether entity properties are picked up by FeGen correctly
 * no matter if they are represented by fields or getters.
 */
@Entity
public class PlainFieldTestEntity {

    @Id
    @GeneratedValue
    public long id;

    @NotNull
    public String notNullField;

    @Nullable
    public String nullableField;

    @NotNull
    public String bothWithNotNullOnField;

    public String getBothWithNotNullOnField() {
        return bothWithNotNullOnField;
    }

    public String bothWithNotNullOnGetter;

    @NotNull
    public String getBothWithNotNullOnGetter() {
        return bothWithNotNullOnGetter;
    }

    public transient String transientField;

    @NotNull
    public transient String transientFieldWithGetter;

    public String getTransientFieldWithGetter() {
        return transientFieldWithGetter;
    }
}
