package com.warework.module.marketing.message;

import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import com.warework.core.util.CommonValueL2Constants;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class GrammarHelper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private GrammarHelper() {
		// DO NOTHING.
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param pageBundle
	 * @param baseMessageKey
	 * @param variables
	 * @param messageBlocks
	 * @param hashKey
	 * @return
	 */
	public static String buildMessage(final ResourceBundle pageBundle, final String baseMessageKey,
			final Map<String, String> variables, final MessageGrammar[] messageBlocks, final String hashKey) {

		//
		int hash = 0;
		if (hashKey != null) {

			//
			final char[] keyChars = hashKey.toCharArray();

			//
			for (int j = 0; j < keyChars.length; j++) {
				hash = hash + keyChars[j];
			}

		}

		//
		final StringBuilder output = new StringBuilder();

		//
		for (int i = 0; i < messageBlocks.length; i++) {

			//
			final MessageGrammar messageBlock = messageBlocks[i];
			if ((messageBlock != null) && (!messageBlock.isRequired())) {
				continue;
			}

			//
			String messageKey = baseMessageKey + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + "block"
					+ ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + (i + 1);
			if ((messageBlock != null) && (messageBlock.getExtensionMessageKey() != null)
					&& (!messageBlock.getExtensionMessageKey().trim().equals(CommonValueL2Constants.STRING_EMPTY))) {
				messageKey = messageKey + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR
						+ messageBlock.getExtensionMessageKey();
			}

			//
			final int maxBlockOptions = Integer.parseInt(pageBundle
					.getString(
							messageKey + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + "max-options")
					.trim());

			//
			int selectedOption = -1;
			if (maxBlockOptions == 1) {
				selectedOption = 1;
			} else if (hash > 0) {
				selectedOption = (hash - (maxBlockOptions * ((int) hash / maxBlockOptions))) + 1;
			} else {
				selectedOption = new Random().nextInt(maxBlockOptions) + 1;
			}

			//
			messageKey = messageKey + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + "option"
					+ ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + selectedOption;

			// Get new line with variables replaced and leading plus trailing spaces (not
			// new line, tabs...just spaces ' ') removed.
			final String line = StringL2Helper
					.replace(pageBundle.getString(updateKey(messageKey, messageBlock)), variables)
					.replaceAll("^ +", CommonValueL2Constants.STRING_EMPTY)
					.replaceAll(" +$", CommonValueL2Constants.STRING_EMPTY);

			// Add new line.
			output.append(line);

			//
			if ((i < (messageBlocks.length - 1)) && (!line.endsWith(StringL2Helper.CHARACTER_NEW_LINE))
					&& (!line.endsWith("\t"))) {
				output.append(StringL2Helper.CHARACTER_SPACE);
			}

		}

		//
		return output.toString();

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param baseKey
	 * @param grammar
	 * @return
	 */
	private static String updateKey(final String baseKey, final MessageGrammar grammar) {
		if (grammar == null) {
			return baseKey;
		} else {

			//
			String outputKey = baseKey;

			//
			if (grammar.getGender() == MessageGrammar.GrammarGender.FEMALE) {
				outputKey = outputKey + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + "female";
			} else if (grammar.getGender() == MessageGrammar.GrammarGender.NEUTRAL) {
				outputKey = outputKey + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + "neutral";
			} else if (grammar.getGender() == MessageGrammar.GrammarGender.INCLUSIVE) {
				outputKey = outputKey + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + "inclusive";
			}

			//
			if (grammar.getNumber() != null) {
				if (grammar.getNumber() == MessageGrammar.GrammarNumber.PLURAL) {
					outputKey = outputKey + ResourceL2Helper.PROPERTIES_FILE_PROPERTY_HIERARCHY_SEPARATOR + "plural";
				}
			}

			//
			return outputKey;

		}
	}

}
