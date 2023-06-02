package com.warework.module.marketing.twitter;

import com.warework.core.util.helper.DateL2Helper;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class TwitterConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * CACHE
	 */

	/**
	 * Number of minutes before update the cache of the rate controller.
	 */
	public static final long CACHE_DEFAULT_LIFETIME_RATE_CONTROLLER = 60 * DateL2Helper.MINUTE_MILLIS;

	/**
	 * Number of minutes before update the cache of the Twitter helper.
	 */
	public static final long CACHE_DEFAULT_LIFETIME_HELPER = 15 * DateL2Helper.MINUTE_MILLIS;

	/*
	 * API RATE LIMITS
	 */

	// TIME_FRAMES

	/**
	 * Spread across a 24 period. There are also separate limits per half-hour
	 * periods, so you won't be able to tweet all 2,400 in one go. The half-hour
	 * limit is unknown for now. Retweets count towards the limit. We define a 30
	 * minute window size to avoid problems.
	 */
	public static final long API_RATE_TIME_FRAME_POST_UPDATE_STATUS = 30 * DateL2Helper.MINUTE_MILLIS;

	/**
	 * Standard Twitter accounts can follow up to 400 accounts per day. If your
	 * account is verified, you can follow up to 1,000 accounts per day.
	 */
	public static final long API_RATE_TIME_FRAME_POST_CREATE_FRIENDSHIP = DateL2Helper.DAY_MILLIS;

	/**
	 * Twitter doesn't state this, but you are likely to get banned if you unfollow
	 * people aggressively. We would recommend sticking well under 400 unfollows per
	 * day.
	 */
	public static final long API_RATE_TIME_FRAME_POST_DESTROY_FRIENDSHIP = DateL2Helper.DAY_MILLIS;

	/**
	 * All GET request windows are 15 minutes in length. These rate limits apply to
	 * the standard API endpoints only, does not apply to premium APIs.
	 */
	public static final long API_RATE_TIME_FRAME_GET_DEFAULT = 15 * DateL2Helper.MINUTE_MILLIS;

	// API CALL RATE LIMITS - GET AND POST OPERATIONS

	/**
	 * Spread across a 24 period. There are also separate limits per half-hour
	 * periods, so you won't be able to tweet all 2,400 in one go. The half-hour
	 * limit is unknown for now. Retweets count towards the limit. We define a 30
	 * minute window size to avoid problems.
	 */
	public static final int API_RATE_LIMIT_POST_RECOMENDED_UPDATE_STATUS_PER_HALF_AN_HOUR = 2400 / (24 * 2);

	/**
	 * Standard Twitter accounts can follow up to 400 accounts per day. If your
	 * account is verified, you can follow up to 1,000 accounts per day.
	 */
	public static final int API_RATE_LIMIT_POST_CREATE_FRIENDSHIP_PER_DAY_STANDARD_ACCOUNT = 400;

	/**
	 * Standard Twitter accounts can follow up to 400 accounts per day. If your
	 * account is verified, you can follow up to 1,000 accounts per day.
	 */
	public static final int API_RATE_LIMIT_POST_CREATE_FRIENDSHIP_PER_DAY_VERIFIED_ACCOUNT = 1000;

	/**
	 * Twitter doesn't state this, but you are likely to get banned if you unfollow
	 * people aggressively. We would recommend sticking well under 400 unfollows per
	 * day.
	 */
	public static final int API_RATE_LIMIT_POST_DESTROY_FRIENDSHIP_PER_DAY = 400;

	/**
	 * 
	 */
	public static final int API_RATE_LIMIT_GET_USER_SHOW = 900;

	/**
	 * 
	 */
	public static final int API_RATE_LIMIT_GET_FRIENDSHIP_SHOW = 180;

	/**
	 * 
	 */
	public static final int API_RATE_LIMIT_GET_FOLLOWERS_IDS = 15;

	/**
	 * 
	 */
	public static final int API_RATE_LIMIT_GET_FRIENDS_IDS = 15;

	// API CALL RATE LIMITS - MISC.

	/**
	 * 
	 */
	public static final int API_RATE_LIMIT_CHARACTER_PER_TWEET = 280;

	/**
	 * According to the author, Rob Brown, the magic number is actually 1,819! This
	 * means if you have 1,819 followers you can break through the 2,000 limit.
	 * Below 1,819 you are capped at following 2,000 people. Above 1,819 you can
	 * follow this number +10% (or 182). At 1,819+10% this means you can follow
	 * 2,001 people and you have just broken the 2,000 limit- hurray! This (as Rob
	 * Brown mentions on
	 * 
	 * This means if you have 1,819 followers you can break through the 2,000 limit.
	 * Below 1,819 you are capped at following 2,000 people. Above 1,819 you can
	 * follow this number +10% (or 182). At 1,819+10% this means you can follow
	 * 2,001 people and you have just broken the 2,000 limit- hurray! This (as Rob
	 * Brown mentions on this post) means for every 10 people that follow you, you
	 * can follow 11.
	 * 
	 * Update - from October 27, 2015, Twitter announced they were increasing this
	 * follow limit from 2,000 to 5,000:
	 * 
	 * I haven't tested this, but I am fairly sure that the same system will be used
	 * for this new limit. To break through the limit you will need to have more
	 * than 4545 people following you before you can break through the 5,000 limit.
	 * Let me know what you think!
	 * 
	 * Personally, I think this is a good thing. I'm a bit concerned when I see
	 * someone with a large number of people they follow- especially if they don't
	 * have engaging content. Twitter is about engagement and networking- it is a
	 * social network after all. Having said that, following a large number of
	 * people isn't always a bad thing. I love to follow new people, particularly
	 * those who Tweet about social media. I manage a large number of followers
	 * using Twitter lists- putting the people I find particularly interesting into
	 * my "awesome" list. Less regularly, I'll check my main Twitter stream, and
	 * perhaps add some cool people to this list.
	 */
	public static final int API_RATE_LIMIT_FOLLOWERS_REQUIRED_TO_INCREASE_FOLLOWING_BASIC_RANGE = 4545;

	/**
	 * 
	 */
	public static final int API_RATE_LIMIT_FOLLOWING_BASIC_RANGE = 5000;

	/*
	 * ERROR CODES
	 */

	/**
	 * Corresponds with HTTP 404. The user is not found.
	 */
	public static final int ERROR_CODE_USER_NOT_FOUND = 50;

	/**
	 * Corresponds with HTTP 403 The user account has been suspended and information
	 * cannot be retrieved.
	 */
	public static final int ERROR_CODE_USER_ACCOUNT_SUSPENDED = 63;

	/*
	 * EMOJIS
	 */

	/**
	 * Twitter ballon emoji.
	 */
	public static final String EMOJI_BALLON = new String(Character.toChars(0x26BD));

	/**
	 * Twitter calendar emoji.
	 */
	public static final String EMOJI_CALENDAR = new String(Character.toChars(0x1F4C5));

	/**
	 * Twitter clock emoji.
	 */
	public static final String EMOJI_CLOCK = new String(Character.toChars(0x1F552));

	/**
	 * Twitter fire emoji.
	 */
	public static final String EMOJI_FIRE = new String(Character.toChars(0x1F525));

	/**
	 * Twitter moon emoji.
	 */
	public static final String EMOJI_MOON = new String(Character.toChars(0x1F319));

	/**
	 * Twitter siren emoji.
	 */
	public static final String EMOJI_SIREN = new String(Character.toChars(0x1F6A8));

	/**
	 * Twitter stadium emoji.
	 */
	public static final String EMOJI_STADIUM = new String(Character.toChars(0x1F3dF));

	/**
	 * Twitter sun emoji.
	 */
	public static final String EMOJI_SUN = new String(Character.toChars(0x2600));

	/**
	 * Twitter tickets emoji.
	 */
	public static final String EMOJI_TICKETS = new String(Character.toChars(0x1F39F));

	/**
	 * Twitter VS emoji.
	 */
	public static final String EMOJI_VERSUS = new String(Character.toChars(0x1F19A));

	/**
	 * Twitter mist emoji.
	 */
	public static final String EMOJI_WEATHER_MIST = new String(Character.toChars(0x1F32B));

	/**
	 * Twitter overcast clouds emoji.
	 */
	public static final String EMOJI_WEATHER_OVERCAST_CLOUDS = new String(Character.toChars(0x2601));

	/**
	 * Twitter rain emoji.
	 */
	public static final String EMOJI_WEATHER_RAIN = new String(Character.toChars(0x1F327));

	/**
	 * Twitter scattered clouds emoji.
	 */
	public static final String EMOJI_WEATHER_SCATTERED_CLOUDS = new String(Character.toChars(0x26C5));

	/**
	 * Twitter snow emoji.
	 */
	public static final String EMOJI_WEATHER_SNOW = new String(Character.toChars(0x1F328));

	/**
	 * Twitter sun emoji.
	 */
	public static final String EMOJI_WEATHER_SUN = EMOJI_SUN;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private TwitterConstants() {
		// DO NOTHING.
	}

}
