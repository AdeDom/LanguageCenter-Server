package com.lc.server.business.business

import com.lc.server.business.model.CommunityAlgorithm
import com.lc.server.business.model.CommunityBusiness
import com.lc.server.data.model.CommunityUserLocalesDb
import com.lc.server.data.model.CommunityUsersDb
import com.lc.server.models.model.Community

interface ServerBusiness {

    fun isValidateJwtIncorrect(token: String): Boolean

    fun isValidateJwtExpires(token: String): Boolean

    fun convertDateTimeLongToString(date: Long?): String?

    fun getAgeInt(time: Long): Int

    fun filterCommunityUsersNew(
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityFriend: List<String>,
    ): List<CommunityUsersDb>

    fun findRatioAlgorithm(communityAlgorithms: List<CommunityAlgorithm>): List<CommunityAlgorithm>

    fun getAlgorithmA(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
    ): List<CommunityBusiness>

    fun getAlgorithmB(
        userId: String,
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityUserLocales: List<CommunityUserLocalesDb>,
    ): List<CommunityBusiness>

    fun getAlgorithmC(
        userId: String,
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityUserLocales: List<CommunityUserLocalesDb>,
    ): List<CommunityBusiness>

    fun getAlgorithmD1(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
    ): List<CommunityBusiness>

    fun getAlgorithmD2(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
    ): List<CommunityBusiness>

    fun getAlgorithmE1(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityMyBirthDate: Long?,
    ): List<CommunityBusiness>

    fun getAlgorithmE2(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityMyBirthDate: Long?,
    ): List<CommunityBusiness>

    fun getAlgorithmF(
        userId: String,
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityUserLocales: List<CommunityUserLocalesDb>,
    ): List<CommunityBusiness>

    fun randomCommunities(communities: List<CommunityBusiness>): List<CommunityBusiness>

    fun mapToCommunities(
        randomCommunities: List<CommunityBusiness>,
        userLocaleCommunity: List<CommunityUserLocalesDb>
    ): List<Community>

    fun isCreatedLessThenThreeDay(date: Long): Boolean

}
