package com.mossy.pokerbot.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: d80050
 * Date: 21/05/13
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
public class Annotations
{

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    @BindingAnnotation
    public @interface NumBoardCards {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    @BindingAnnotation
    public @interface NumPlayers {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    @BindingAnnotation
    public @interface FiveCardEvaluator {}
}
