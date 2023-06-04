package com.warework.module.marketing.message;

/**
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class MessageGrammar {

	// ///////////////////////////////////////////////////////////////////
	// ENUMERATIONS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @author jschiaffino
	 *
	 */
	public enum GrammarGender {

		/**
		 * Male gender.
		 */
		MALE,

		/**
		 * Female gender.
		 */
		FEMALE,

		/**
		 * Neutral gender.
		 */
		NEUTRAL,

		/**
		 * The statement includes the gender (for example, with an article) so there is
		 * no need to specify in other paragraph blocks the gender.
		 */
		INCLUSIVE

	}

	/**
	 * 
	 * @author jschiaffino
	 *
	 */
	public enum GrammarNumber {
		SINGULAR, PLURAL
	}

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//

	public static final MessageGrammar NOT_REQUIRED = new MessageGrammar(null, null, null, false);

	public static final MessageGrammar WITHOUT_GRAMMAR = new MessageGrammar(null, null, null, true);

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	//
	private String extensionMessageKey;

	//
	private GrammarGender gender;

	//
	private GrammarNumber number;

	//
	private boolean required = true;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public MessageGrammar() {

	}

	/**
	 * 
	 * @param gender
	 */
	public MessageGrammar(final GrammarGender gender) {
		this(gender, (Number) null);
	}

	/**
	 * 
	 * @param number
	 */
	public MessageGrammar(final Number number) {
		this(null, number);
	}

	/**
	 * 
	 * @param number
	 */
	public MessageGrammar(final GrammarNumber number) {
		this(null, number);
	}

	/**
	 * 
	 * @param gender
	 * @param number
	 */
	public MessageGrammar(final GrammarGender gender, final GrammarNumber number) {

		//
		this();

		//
		this.gender = gender;
		this.number = number;

	}

	/**
	 * 
	 * @param gender
	 * @param number
	 */
	public MessageGrammar(final GrammarGender gender, final Number number) {

		//
		this();

		//
		this.gender = gender;

		//
		if (number != null) {
			if (number.doubleValue() == 1.0) {
				this.number = GrammarNumber.SINGULAR;
			} else {
				this.number = GrammarNumber.PLURAL;
			}
		}

	}

	/**
	 * 
	 * @param extensionMessageKey
	 * @param gender
	 * @param number
	 */
	public MessageGrammar(final String extensionMessageKey, final GrammarGender gender, final Number number) {

		//
		this(gender, number);

		//
		this.extensionMessageKey = extensionMessageKey;

	}

	/**
	 * 
	 * @param extensionMessageKey
	 * @param gender
	 * @param number
	 */
	public MessageGrammar(final String extensionMessageKey, final GrammarGender gender, final Number number,
			final boolean required) {

		//
		this(gender, number);

		//
		this.extensionMessageKey = extensionMessageKey;

		//
		this.required = required;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * @return the extensionMessageKey
	 */
	public String getExtensionMessageKey() {
		return extensionMessageKey;
	}

	/**
	 * @return the gender
	 */
	public GrammarGender getGender() {
		return gender;
	}

	/**
	 * @return the number
	 */
	public GrammarNumber getNumber() {
		return number;
	}

	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

}
