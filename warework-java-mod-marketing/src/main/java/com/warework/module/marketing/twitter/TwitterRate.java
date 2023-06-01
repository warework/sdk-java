package com.warework.module.marketing.twitter;

import java.io.Serializable;

import com.warework.module.business.api.ApiException;
import com.warework.module.business.api.ApiRate;

import twitter4j.User;

/**
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class TwitterRate implements Serializable {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private static final long serialVersionUID = 1865102578809691452L;

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	//
	private TwitterHandler twitterHandler;

	//
	private User authenticatingUser;

	//
	private ApiRate tweetsRate, followRate, unfollowRate, userShowRate, friendshipShowRate, followersIdsRate,
			friendsIdsRate;

	// Last time cache was updated.
	private long cacheStartTime;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	private TwitterRate() {
		cacheStartTime = System.currentTimeMillis();
	}

	/**
	 * 
	 * @param handler
	 * @param timeFrame
	 * @throws ApiException
	 */
	TwitterRate(final TwitterHandler handler, final long timeFrame) throws ApiException {

		//
		this();

		// Load authenticating user.
		authenticatingUser = handler.showUser(handler.getAuthenticatingUserId());

		//
		twitterHandler = handler;

		//
		if (timeFrame < 0) {

			/*
			 * POST OPERATIONS
			 */

			//
			tweetsRate = new ApiRate(TwitterConstants.API_RATE_TIME_FRAME_POST_UPDATE_STATUS,
					TwitterConstants.API_RATE_LIMIT_POST_RECOMENDED_UPDATE_STATUS_PER_HALF_AN_HOUR);

			//
			if (authenticatingUser.isVerified()) {
				followRate = createFollowRate(TwitterConstants.API_RATE_TIME_FRAME_POST_CREATE_FRIENDSHIP,
						TwitterConstants.API_RATE_LIMIT_POST_CREATE_FRIENDSHIP_PER_DAY_VERIFIED_ACCOUNT);
			} else {
				followRate = createFollowRate(TwitterConstants.API_RATE_TIME_FRAME_POST_CREATE_FRIENDSHIP,
						TwitterConstants.API_RATE_LIMIT_POST_CREATE_FRIENDSHIP_PER_DAY_STANDARD_ACCOUNT);
			}

			//
			unfollowRate = new ApiRate(TwitterConstants.API_RATE_TIME_FRAME_POST_DESTROY_FRIENDSHIP,
					TwitterConstants.API_RATE_LIMIT_POST_DESTROY_FRIENDSHIP_PER_DAY);

			/*
			 * GET OPERATIONS
			 */

			//
			userShowRate = new ApiRate(TwitterConstants.API_RATE_TIME_FRAME_GET_DEFAULT,
					TwitterConstants.API_RATE_LIMIT_GET_USER_SHOW);

			//
			friendshipShowRate = new ApiRate(TwitterConstants.API_RATE_TIME_FRAME_GET_DEFAULT,
					TwitterConstants.API_RATE_LIMIT_GET_FRIENDSHIP_SHOW);

			//
			followersIdsRate = new ApiRate(TwitterConstants.API_RATE_TIME_FRAME_GET_DEFAULT,
					TwitterConstants.API_RATE_LIMIT_GET_FOLLOWERS_IDS);

			//
			friendsIdsRate = new ApiRate(TwitterConstants.API_RATE_TIME_FRAME_GET_DEFAULT,
					TwitterConstants.API_RATE_LIMIT_GET_FRIENDS_IDS);

		} else {

			/*
			 * POST OPERATIONS
			 */

			//
			tweetsRate = new ApiRate(timeFrame,
					(int) ((TwitterConstants.API_RATE_LIMIT_POST_RECOMENDED_UPDATE_STATUS_PER_HALF_AN_HOUR * timeFrame)
							/ TwitterConstants.API_RATE_TIME_FRAME_POST_UPDATE_STATUS));

			//
			if (authenticatingUser.isVerified()) {
				followRate = createFollowRate(timeFrame,
						(int) ((TwitterConstants.API_RATE_LIMIT_POST_CREATE_FRIENDSHIP_PER_DAY_VERIFIED_ACCOUNT
								* timeFrame) / TwitterConstants.API_RATE_TIME_FRAME_POST_CREATE_FRIENDSHIP));
			} else {
				followRate = createFollowRate(timeFrame,
						(int) ((TwitterConstants.API_RATE_LIMIT_POST_CREATE_FRIENDSHIP_PER_DAY_STANDARD_ACCOUNT
								* timeFrame) / TwitterConstants.API_RATE_TIME_FRAME_POST_CREATE_FRIENDSHIP));
			}

			//
			unfollowRate = new ApiRate(timeFrame,
					(int) ((TwitterConstants.API_RATE_LIMIT_POST_DESTROY_FRIENDSHIP_PER_DAY * timeFrame)
							/ TwitterConstants.API_RATE_TIME_FRAME_POST_DESTROY_FRIENDSHIP));

			/*
			 * GET OPERATIONS
			 */

			//
			userShowRate = new ApiRate(timeFrame, (int) ((TwitterConstants.API_RATE_LIMIT_GET_USER_SHOW * timeFrame)
					/ TwitterConstants.API_RATE_TIME_FRAME_GET_DEFAULT));

			//
			friendshipShowRate = new ApiRate(timeFrame,
					(int) ((TwitterConstants.API_RATE_LIMIT_GET_FRIENDSHIP_SHOW * timeFrame)
							/ TwitterConstants.API_RATE_TIME_FRAME_GET_DEFAULT));

			//
			followersIdsRate = new ApiRate(timeFrame,
					(int) ((TwitterConstants.API_RATE_LIMIT_GET_FOLLOWERS_IDS * timeFrame)
							/ TwitterConstants.API_RATE_TIME_FRAME_GET_DEFAULT));

			//
			friendsIdsRate = new ApiRate(timeFrame, (int) ((TwitterConstants.API_RATE_LIMIT_GET_FRIENDS_IDS * timeFrame)
					/ TwitterConstants.API_RATE_TIME_FRAME_GET_DEFAULT));

		}

		/*
		 * SETUP INITIAL EXECUTED OPERATIONS
		 */

		//
		userShowRate.increase();

	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param operationType
	 * @return
	 */
	boolean increase(final TwitterOperation operationType) {
		if (operationType == TwitterOperation.POST_UPDATE_STATUS) {
			return tweetsRate.increase();
		} else if (operationType == TwitterOperation.POST_CREATE_FRIENDSHIP) {
			return followRate.increase();
		} else if (operationType == TwitterOperation.POST_DESTROY_FRIENDSHIP) {
			return unfollowRate.increase();
		} else if (operationType == TwitterOperation.GET_USER_SHOW) {
			return userShowRate.increase();
		} else if (operationType == TwitterOperation.GET_FRIENDSHIP_SHOW) {
			return friendshipShowRate.increase();
		} else if (operationType == TwitterOperation.GET_FOLLOWERS_IDS) {
			return followersIdsRate.increase();
		} else {
			return friendsIdsRate.increase();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	User getAuthenticatingUser() {
		return authenticatingUser;
	}

	/**
	 * 
	 * @param operationType
	 * @return
	 */
	ApiRate getApiRate(final TwitterOperation operationType) {
		if (operationType == TwitterOperation.POST_UPDATE_STATUS) {
			return tweetsRate;
		} else if (operationType == TwitterOperation.POST_CREATE_FRIENDSHIP) {
			return followRate;
		} else if (operationType == TwitterOperation.POST_DESTROY_FRIENDSHIP) {
			return unfollowRate;
		} else if (operationType == TwitterOperation.GET_USER_SHOW) {
			return userShowRate;
		} else if (operationType == TwitterOperation.GET_FRIENDSHIP_SHOW) {
			return friendshipShowRate;
		} else if (operationType == TwitterOperation.GET_FOLLOWERS_IDS) {
			return followersIdsRate;
		} else {
			return friendsIdsRate;
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @throws ApiException
	 */
	private void synchronize() throws ApiException {
		if (((System.currentTimeMillis() - cacheStartTime) >= TwitterConstants.CACHE_DEFAULT_LIFETIME_RATE_CONTROLLER)
				&& (userShowRate.increase())) {

			// Update authenticating user cache.
			authenticatingUser = twitterHandler.showUser(authenticatingUser.getId());

			// Set authenticating user cache time.
			cacheStartTime = System.currentTimeMillis();

		}
	}

	/**
	 * 
	 * @param timeFrame
	 * @param operations
	 * @return
	 */
	private ApiRate createFollowRate(final long timeFrame, final int operations) {
		return new ApiRate(timeFrame, operations) {

			// ///////////////////////////////////////////////////////////
			// CONSTANTS
			// ///////////////////////////////////////////////////////////

			/**
			 * 
			 */
			private static final long serialVersionUID = -3488315569119289844L;

			// ///////////////////////////////////////////////////////////
			// PROTECTED METHODS
			// ///////////////////////////////////////////////////////////

			/**
			 * 
			 * @return
			 */
			protected final boolean validate() {

				//
				try {
					synchronize();
				} catch (ApiException e) {
					return false;
				}

				// Get the number of account followers (users that follow the account user).
				final int followers = authenticatingUser.getFollowersCount();

				// Get the number of users the user follows.
				final int currentFollowed = authenticatingUser.getFriendsCount();

				//
				int followedLimit = -1;
				if (followers >= TwitterConstants.API_RATE_LIMIT_FOLLOWERS_REQUIRED_TO_INCREASE_FOLLOWING_BASIC_RANGE) {
					followedLimit = TwitterConstants.API_RATE_LIMIT_FOLLOWERS_REQUIRED_TO_INCREASE_FOLLOWING_BASIC_RANGE
							+ ((followers * 10) / 100);
				} else {
					followedLimit = TwitterConstants.API_RATE_LIMIT_FOLLOWING_BASIC_RANGE;
				}

				//
				return (currentFollowed < followedLimit);

			}

		};
	}

}
