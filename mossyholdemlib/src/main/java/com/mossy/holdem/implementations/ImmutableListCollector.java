package com.mossy.holdem.implementations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by willrubens on 13/05/15.
 */
public class ImmutableListCollector<T> implements Collector<T, Builder<T>, ImmutableList<T>> {
    @Override
    public Supplier<Builder<T>> supplier() {
        return Builder::new;
    }

    @Override
    public BiConsumer<Builder<T>, T> accumulator() {
        return (b, e) -> b.add(e);
    }

    @Override
    public BinaryOperator<Builder<T>> combiner() {
        return (b1, b2) -> b1.addAll(b2.build());
    }

    @Override
    public Function<Builder<T>, ImmutableList<T>> finisher() {
        return Builder::build;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return ImmutableSet.of();
    }
}