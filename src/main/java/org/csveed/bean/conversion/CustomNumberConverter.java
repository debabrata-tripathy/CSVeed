package org.csveed.bean.conversion;

import java.text.NumberFormat;

import static org.csveed.bean.conversion.ConversionUtil.hasText;

public class CustomNumberConverter extends AbstractConverter<Number> {

    private final Class<? extends Number> numberClass;

    private final NumberFormat numberFormat;

    private final boolean allowEmpty;

    public CustomNumberConverter(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberConverter(Class<? extends Number> numberClass,
                              NumberFormat numberFormat, boolean allowEmpty) throws IllegalArgumentException {

        if (numberClass == null || !Number.class.isAssignableFrom(numberClass)) {
            throw new IllegalArgumentException("Property class must be a subclass of Number");
        }
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public Number fromString(String text) throws Exception {
        if (this.allowEmpty && !hasText(text)) {
            return null;
        }
        else if (this.numberFormat != null) {
            return determineValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        }
        else {
            return determineValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

    @Override
    public Class getType() {
        return numberClass;
    }

    public Number determineValue(Object value) {
        if (value instanceof Number) {
            return NumberUtils.convertNumberToTargetClass((Number) value, this.numberClass);
        }
        return null;
    }

//    @Override
//    public String toString(Number value) throws Exception {
//        if (value == null) {
//            return "";
//        }
//        if (this.numberFormat != null) {
//            return this.numberFormat.format(value);
//        }
//        else {
//            return value.toString();
//        }
//    }

}
