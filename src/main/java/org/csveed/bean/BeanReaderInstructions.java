package org.csveed.bean;

import org.csveed.bean.conversion.Converter;

import java.beans.PropertyEditor;
import java.util.Locale;

/**
* These instructions are used to power the {@link BeanReader}. Note that the instructions are also used
* internally if annotations are used.
* @author Robert Bor
*/
public interface BeanReaderInstructions {

    /**
    * Makes sure that the first readable line is interpreted as the header line. That line will not be read
    * as content. This method is called whenever {@link org.csveed.annotations.CsvFile#useHeader()}
    * is used. The default value for this setting is true. This call is a facade for
     * {@link org.csveed.row.RowReaderInstructions#setUseHeader(boolean)}.
    * @param useHeader true if the header is interpreted and used
    * @return convenience for chaining
    */
    BeanReaderInstructions setUseHeader(boolean useHeader);

    /**
    * Sets the start row of the CSV file. If {@link #setUseHeader(boolean)} == true, this will be the header
    * row and the next ones are all content rows. This method is called whenever
    * {@link org.csveed.annotations.CsvFile#startRow()} is used. The default value for this
    * setting is 1. This call is a facade for {@link org.csveed.row.RowReaderInstructions#setStartRow(int)}.
    * @param startRow the first row to start reading, including the header row
    * @return convenience for chaining
    */
    BeanReaderInstructions setStartRow(int startRow);

    /**
    * Sets the character that will be interpreted as an escape symbol while within a quoted field. This
    * method is called whenever {@link org.csveed.annotations.CsvFile#escape()} is used. The
    * default value for this setting is a double quote (") symbol. This call is a facade for
    * {@link org.csveed.row.RowReaderInstructions#setEscape(char)}.
    * @param symbol the symbol to use for escaping characters within a quoted field
    * @return convenience for chaining
    */
    BeanReaderInstructions setEscape(char symbol);

    /**
    * Sets the character that will be interpreted as a quote symbol, signifying either the start or the
    * end of a quoted field. This method is called whenever {@link org.csveed.annotations.CsvFile#quote()}
    * is used. The default value for this setting is a double quote (") symbol. This call is a facade for
    * {@link org.csveed.row.RowReaderInstructions#setQuote(char)}.
    * @param symbol the symbol to use for indicating start/end of a quoted field
    * @return convenience for chaining
    */
    BeanReaderInstructions setQuote(char symbol);

    /**
    * Sets the character that will be interpreted as a separator between cells. This method is called whenever
    * {@link org.csveed.annotations.CsvFile#separator()} is used. The default value for this
    * setting is a semi-colon (;). This call is a facade for {@link org.csveed.row.RowReaderInstructions#setSeparator(char)}.
    * @param symbol the symbol to use as a separator between cells
    * @return convenience for chaining
    */
    BeanReaderInstructions setSeparator(char symbol);

    /**
    * Sets the character that will be interpreted as a comment field on the first position of a row.
    * This method is called whenever {@link org.csveed.annotations.CsvFile#comment()} is used.
    * The default value for this setting is a hashtag (#).
    * @param symbol the symbol to use as the 0-position comment marker
    * @return convenience for chaining
    */
    BeanReaderInstructions setComment(char symbol);

    /**
    * Sets the characters (plural) that will be interpreted as end-of-line markers (unless within a quoted
    * field). This method is called whenever {@link org.csveed.annotations.CsvFile#endOfLine()}
    * is used. The default values for this setting are \r and \n.  This call is a facade for
    * {@link org.csveed.row.RowReaderInstructions#setEndOfLine(char[])}.
    * @param symbols the symbol to interpret as end-of-line markers (unless within a quoted field)
    * @return convenience for chaining
    */
    BeanReaderInstructions setEndOfLine(char[] symbols);

    /**
    * Determines whether empty lines must be skipped or treated as single-column rows. This method is called
    * whenever {@link org.csveed.annotations.CsvFile#skipEmptyLines()} is used. The default
    * value for this setting is to skip the empty lines.
    * @param skip true to skip empty lines, false to treat as single-column rows
    * @return convenience for chaining
    */
    BeanReaderInstructions skipEmptyLines(boolean skip);

    /**
    * Determines whether comment lines must be skipped. This method is called whenever
    * {@link org.csveed.annotations.CsvFile#skipCommentLines()}  is used. The default
    * value for this setting is to skip comment lines. This method exists to guarantee that lines are
    * not accidentally treated as comment lines.
    * @param skip true to skip comment lines, identified as starting with a comment marker
    * @return convenience for chaining
    */
    BeanReaderInstructions skipCommentLines(boolean skip);

    /**
    * Determines which mapping strategy is to be employed for mapping cells to bean properties. This
    * method is called whenever {@link org.csveed.annotations.CsvFile#mappingStrategy()} is
    * used. The default mapping strategy is {@link org.csveed.bean.ColumnIndexMapper}, which
    * looks at either the position of a property within the class or the custom index if
    * {@link org.csveed.annotations.CsvCell#columnIndex()} or {@link #mapColumnIndexToProperty(int, String)}
    * has been set.
    * @param mapper the mapping strategy to employ for mapping cells to bean properties
    * @return convenience for chaining
    */
    BeanReaderInstructions setMapper(Class<? extends AbstractMapper> mapper);

    /**
    * Determines what dateformat to apply to the cell value before storing it as a date. This method is called
    * whenever {@link org.csveed.annotations.CsvDate} is used. The default for date format is
    * dd-MM-yyyy.
    * @param propertyName the name of the property to write the date to
    * @param dateFormat the date format to apply for parsing the date value
    * @return convenience for chaining
    */
    BeanReaderInstructions setDate(String propertyName, String dateFormat);

    /**
    * Determines what Locale to apply to the cell value before converting it to a number. This method is called
    * whenever {@link org.csveed.annotations.CsvLocalizedNumber} is used. The default for Locale is the Locale
    * of the server.
    * @param propertyName the name of the property to write the data to
    * @param locale the Locale to apply for converting the number
    */
    BeanReaderInstructions setLocalizedNumber(String propertyName, Locale locale);

    /**
    * Determines if the field is required. If so, the cell may not be empty and a
    * {@link org.csveed.report.CsvException} will be thrown if this occurs. This method is called
    * whenever {@link org.csveed.annotations.CsvCell#required()} is used. The default for a property
    * is false.
    * @param propertyName property for which the requirement applies
    * @param required whether the cell must be not-null
    * @return convenience for chaining
    */
    BeanReaderInstructions setRequired(String propertyName, boolean required);

    /**
    * Sets a custom {@link PropertyEditor} for the property. This PropertyEditor is called to convert the
    * text to the type of the property and set it on the bean. This method is called whenever
    * {@link org.csveed.annotations.CsvConverter#converter()} is used. The default for a property
    * is based on the wonderful set of PropertyEditors that Spring offers, which is all basics and some extras
    * as well.
    * @param propertyName property to which the converter must be applied
    * @param converter PropertyEditor to apply to the property
    * @return convenience for chaining
    */
    BeanReaderInstructions setConverter(String propertyName, Converter converter);

    /**
    * Sets a field to be ignored for purposes of mapping. This method is called whenever
    * {@link org.csveed.annotations.CsvIgnore)} is used. By default none of the fields are ignored
    * unless, custom instructions are used. In this case, all fields are ignored by default.
    * @param propertyName property which must be ignored for mapping
    * @return convenience for chaining
    */
    BeanReaderInstructions ignoreProperty(String propertyName);

    /**
    * Maps a column in the CSV to a specific property. This method is called whenever
    * {@link org.csveed.annotations.CsvCell#columnIndex()} is used. By default there is NO mapping
    * when custom instructions are used, so you should roll your own. Note that column indexes are
    * 1-based, not 0-based
    * @param propertyName property to which the index-based mapping must be applied
    * @return convenience for chaining
    */
    BeanReaderInstructions mapColumnIndexToProperty(int columnIndex, String propertyName);

    /**
    * Maps a column name (which is found in the header) to a specific property. Note that to use this, headers
    * must be enabled. This method is called whenever {@link org.csveed.annotations.CsvCell#columnName()}
    * is used. By default there is NO mapping when custom instructions are used, so you should roll your own.
    * Also, don't forget to {@link #setMapper(Class)} to
    * {@link org.csveed.bean.ColumnNameMapper} for this to work.
    * @param propertyName property to which the name-based mapping must be applied
    * @return convenience for chaining
    */
    BeanReaderInstructions mapColumnNameToProperty(String columnName, String propertyName);

    Class getBeanClass();

}
