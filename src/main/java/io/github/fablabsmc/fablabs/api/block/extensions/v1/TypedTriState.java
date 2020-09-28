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
