/*
 * Copyright 2020 FabLabsMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.fablabsmc.fablabs.api.block.extensions.v1;

import net.fabricmc.fabric.api.util.TriState;

public final class TypedTriState<T> {
	public static <T> TypedTriState<T> trueValue(T value) {
		return new TypedTriState<>(TriState.TRUE, value);
	}

	public static <T> TypedTriState<T> defaultValue(T value) {
		return new TypedTriState<>(TriState.DEFAULT, value);
	}

	public static <T> TypedTriState<T> falseValue(T value) {
		return new TypedTriState<>(TriState.FALSE, value);
	}

	private final TriState result;
	private final T value;

	private TypedTriState(TriState result, T value) {
		this.result = result;
		this.value = value;
	}

	public TriState getResult() {
		return this.result;
	}

	public T getValue() {
		return this.value;
	}
}
