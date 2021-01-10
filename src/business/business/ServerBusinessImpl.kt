package com.lc.server.business.business

import com.auth0.jwt.JWT
import com.lc.server.business.model.CommunityAlgorithm
import com.lc.server.business.model.CommunityBusiness
import com.lc.server.data.map.Mapper
import com.lc.server.data.model.CommunityUserLocalesDb
import com.lc.server.data.model.CommunityUsersDb
import com.lc.server.models.model.Community
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.util.LanguageCenterConstant
import java.text.SimpleDateFormat
import java.util.*

class ServerBusinessImpl : ServerBusiness {

    override fun isValidateJwtIncorrect(token: String): Boolean = try {
        JWT().decodeJwt(token).getClaim("user_id").asString() == null
    } catch (e: Throwable) {
        true
    }

    override fun isValidateJwtExpires(token: String): Boolean = try {
        JWT().decodeJwt(token).getClaim("exp").asDate() < Date()
    } catch (e: Throwable) {
        true
    }

    override fun convertDateTimeLongToString(date: Long?): String? = date?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale("th", "TH")).format(date)
    }

    override fun getAgeInt(time: Long): Int {
        val now = System.currentTimeMillis()
        val timeBetween: Long = now - time
        val yearsBetween: Double = timeBetween / 3.15576e+10
        val age = kotlin.math.floor(yearsBetween).toInt()

        return age
    }

    override fun filterCommunityUsersNew(
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityFriend: List<String>,
    ): List<CommunityUsersDb> {
        val list = mutableListOf<CommunityUsersDb>()
        getCommunityFriend.forEach { friendId ->
            getCommunityUsers.filter { it.userId == friendId }
                .onEach { list.add(it) }
        }

        return getCommunityUsers - list
    }

    override fun findRatioAlgorithm(communityAlgorithms: List<CommunityAlgorithm>): List<CommunityAlgorithm> {
        val allAlgorithm = listOf(
            LanguageCenterConstant.ALGORITHM_A,
            LanguageCenterConstant.ALGORITHM_B,
            LanguageCenterConstant.ALGORITHM_C,
            LanguageCenterConstant.ALGORITHM_D1,
            LanguageCenterConstant.ALGORITHM_D2,
            LanguageCenterConstant.ALGORITHM_E1,
            LanguageCenterConstant.ALGORITHM_E2,
            LanguageCenterConstant.ALGORITHM_F,
        )

        val originalRatio = LanguageCenterConstant.COMMUNITY_LIST_MAX / allAlgorithm.size

        val calAlgorithm = mutableListOf<CommunityAlgorithm>()
        allAlgorithm.forEach { calAlgorithm.add(CommunityAlgorithm(it, originalRatio)) }

        val communityAlgorithmsPopular = communityAlgorithms as MutableList
        allAlgorithm.forEach { algorithmName ->
            if (communityAlgorithmsPopular.singleOrNull { it.algorithmName == algorithmName } == null) {
                communityAlgorithmsPopular.add(CommunityAlgorithm(algorithmName = algorithmName))
            }
        }

        val populars = communityAlgorithmsPopular
            .filter { it.algorithmQty > originalRatio }
            .take(LanguageCenterConstant.ALGORITHM_TAKE_POPULAR)

        when (populars.size) {
            1 -> {
                // + first
                val (algorithmNameFirst, algorithmQtyFirst) = communityAlgorithms.first()
                val itemFirst = calAlgorithm.single { it.algorithmName == algorithmNameFirst }
                calAlgorithm[calAlgorithm.indexOf(itemFirst)] =
                    CommunityAlgorithm(algorithmNameFirst, algorithmQtyFirst)

                // - last
                val popularQty = algorithmQtyFirst - originalRatio
                val qtyLast = if ((originalRatio - popularQty) < LanguageCenterConstant.ALGORITHM_QUANTITY_MIN) {
                    LanguageCenterConstant.ALGORITHM_QUANTITY_MIN
                } else {
                    (originalRatio - popularQty)
                }
                val algorithmNameLast = communityAlgorithms.last().algorithmName
                val itemLast = calAlgorithm.single { it.algorithmName == algorithmNameLast }
                calAlgorithm[calAlgorithm.indexOf(itemLast)] = CommunityAlgorithm(algorithmNameLast, qtyLast)
            }
            2 -> {
                // -- session 1 -- //

                // + first
                val (algorithmNameFirst, algorithmQtyFirst) = communityAlgorithms.first()
                val itemFirst = calAlgorithm.single { it.algorithmName == algorithmNameFirst }
                calAlgorithm[calAlgorithm.indexOf(itemFirst)] =
                    CommunityAlgorithm(algorithmNameFirst, algorithmQtyFirst)

                // - last
                val popularQty = algorithmQtyFirst - originalRatio
                val qtyLast = if ((originalRatio - popularQty) < LanguageCenterConstant.ALGORITHM_QUANTITY_MIN) {
                    LanguageCenterConstant.ALGORITHM_QUANTITY_MIN
                } else {
                    (originalRatio - popularQty)
                }
                val algorithmNameLast = communityAlgorithms.last().algorithmName
                val itemLast = calAlgorithm.single { it.algorithmName == algorithmNameLast }
                calAlgorithm[calAlgorithm.indexOf(itemLast)] = CommunityAlgorithm(algorithmNameLast, qtyLast)

                // -- session 2 -- //

                // + second
                val (algorithmNameFirst2, algorithmQtyFirst2) = communityAlgorithms[1]
                val itemFirst2 = calAlgorithm.single { it.algorithmName == algorithmNameFirst2 }
                calAlgorithm[calAlgorithm.indexOf(itemFirst2)] =
                    CommunityAlgorithm(algorithmNameFirst2, algorithmQtyFirst2)

                // - before last
                val popularQty2 = algorithmQtyFirst2 - originalRatio
                val qtyLast2 = if ((originalRatio - popularQty2) < LanguageCenterConstant.ALGORITHM_QUANTITY_MIN) {
                    LanguageCenterConstant.ALGORITHM_QUANTITY_MIN
                } else {
                    (originalRatio - popularQty2)
                }
                val algorithmNameLast2 = communityAlgorithms[communityAlgorithms.lastIndex - 1].algorithmName
                val itemLast2 = calAlgorithm.single { it.algorithmName == algorithmNameLast2 }
                calAlgorithm[calAlgorithm.indexOf(itemLast2)] = CommunityAlgorithm(algorithmNameLast2, qtyLast2)
            }
        }

        communityAlgorithmsPopular.forEachIndexed { index, communityAlgorithm ->
            println("database${index.plus(1)} : $communityAlgorithm")
        }

        calAlgorithm.forEachIndexed { index, communityAlgorithm ->
            println("findRatioAlgorithm${index.plus(1)} : $communityAlgorithm")
        }

        return calAlgorithm
    }

    override fun getAlgorithmA(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
    ): List<CommunityBusiness> {
        val algorithmQty = ratioAlgorithm.single { it.algorithmName == LanguageCenterConstant.ALGORITHM_A }.algorithmQty

        val list = getCommunityUsers
            .filter {
                isCreatedLessThenThreeDay(it.created)
            }
            .map {
                Mapper.toCommunityAlgorithmBusiness(it).copy(algorithm = LanguageCenterConstant.ALGORITHM_A)
            }
            .takeLast(algorithmQty)

        println("getAlgorithmA ${list.size} : $list")

        return list
    }

    override fun getAlgorithmB(
        userId: String,
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityUserLocales: List<CommunityUserLocalesDb>,
    ): List<CommunityBusiness> {
        val localeMeTh = getCommunityUserLocales
            .singleOrNull {
                it.userId == userId
                        && it.localeType == LanguageCenterConstant.LOCALE_LEARNING
                        && it.locale == LanguageCenterConstant.LOCALE_THAI
            }

        val localeMeEn = getCommunityUserLocales
            .singleOrNull {
                it.userId == userId
                        && it.localeType == LanguageCenterConstant.LOCALE_LEARNING
                        && it.locale == LanguageCenterConstant.LOCALE_ENGLISH
            }

        val localeLearnings = mutableListOf<CommunityUserLocalesDb>()

        when {
            localeMeTh != null -> {
                getCommunityUserLocales
                    .filter {
                        it.userId != userId
                                && it.localeType == LanguageCenterConstant.LOCALE_NATIVE
                                && it.locale == LanguageCenterConstant.LOCALE_THAI
                                && it.level > localeMeTh.level
                    }
                    .onEach {
                        localeLearnings.add(it)
                    }
            }
            localeMeEn != null -> {
                getCommunityUserLocales
                    .filter {
                        it.userId != userId
                                && it.localeType == LanguageCenterConstant.LOCALE_NATIVE
                                && it.locale == LanguageCenterConstant.LOCALE_ENGLISH
                                && it.level > localeMeEn.level
                    }
                    .onEach {
                        localeLearnings.add(it)
                    }
            }
        }

        val algorithmQty = ratioAlgorithm.single { it.algorithmName == LanguageCenterConstant.ALGORITHM_B }.algorithmQty

        val list = mutableListOf<CommunityUsersDb>()
        while (list.size < algorithmQty) {
            val userIdLocaleNative = localeLearnings.random().userId
            getCommunityUsers.singleOrNull { it.userId == userIdLocaleNative }?.let { list.add(it) }
        }

        println("getAlgorithmB ${list.size} : $list")

        return list
            .map {
                Mapper.toCommunityAlgorithmBusiness(it).copy(algorithm = LanguageCenterConstant.ALGORITHM_B)
            }
    }

    override fun getAlgorithmC(
        userId: String,
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityUserLocales: List<CommunityUserLocalesDb>,
    ): List<CommunityBusiness> {

        val localeMeTh = getCommunityUserLocales
            .singleOrNull {
                it.userId == userId
                        && it.localeType == LanguageCenterConstant.LOCALE_LEARNING
                        && it.locale == LanguageCenterConstant.LOCALE_THAI
            }

        val localeMeEn = getCommunityUserLocales
            .singleOrNull {
                it.userId == userId
                        && it.localeType == LanguageCenterConstant.LOCALE_LEARNING
                        && it.locale == LanguageCenterConstant.LOCALE_ENGLISH
            }

        val localeLearnings = mutableListOf<CommunityUserLocalesDb>()

        when {
            localeMeTh != null -> {
                getCommunityUserLocales
                    .filter {
                        it.userId != userId
                                && it.localeType == LanguageCenterConstant.LOCALE_NATIVE
                                && it.locale == LanguageCenterConstant.LOCALE_THAI
                    }
                    .onEach {
                        localeLearnings.add(it)
                    }
            }
            localeMeEn != null -> {
                getCommunityUserLocales
                    .filter {
                        it.userId != userId
                                && it.localeType == LanguageCenterConstant.LOCALE_NATIVE
                                && it.locale == LanguageCenterConstant.LOCALE_ENGLISH
                    }
                    .onEach {
                        localeLearnings.add(it)
                    }
            }
        }

        val algorithmQty = ratioAlgorithm.single { it.algorithmName == LanguageCenterConstant.ALGORITHM_C }.algorithmQty

        val list = mutableListOf<CommunityUsersDb>()
        while (list.size < algorithmQty) {
            val userIdLocaleNative = localeLearnings.random().userId
            getCommunityUsers.singleOrNull { it.userId == userIdLocaleNative }?.let { list.add(it) }
        }

        println("getAlgorithmC ${list.size} : $list")

        return list
            .map {
                Mapper.toCommunityAlgorithmBusiness(it).copy(algorithm = LanguageCenterConstant.ALGORITHM_C)
            }
    }

    override fun getAlgorithmD1(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>
    ): List<CommunityBusiness> {
        val genderMale = getCommunityUsers
            .filter { it.gender == LanguageCenterConstant.GENDER_MALE }

        val algorithmQty = ratioAlgorithm
            .single { it.algorithmName == LanguageCenterConstant.ALGORITHM_D1 }
            .algorithmQty

        val list = mutableListOf<CommunityUsersDb>()
        while (list.size < algorithmQty) {
            list.add(genderMale.random())
        }

        println("getAlgorithmD1 ${list.size} : $list")

        return list
            .map {
                Mapper.toCommunityAlgorithmBusiness(it).copy(algorithm = LanguageCenterConstant.ALGORITHM_D1)
            }
    }

    override fun getAlgorithmD2(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>
    ): List<CommunityBusiness> {
        val genderFemale = getCommunityUsers
            .filter { it.gender == LanguageCenterConstant.GENDER_FEMALE }

        val algorithmQty = ratioAlgorithm
            .single { it.algorithmName == LanguageCenterConstant.ALGORITHM_D2 }
            .algorithmQty

        val list = mutableListOf<CommunityUsersDb>()
        while (list.size < algorithmQty) {
            list.add(genderFemale.random())
        }

        println("getAlgorithmD2 ${list.size} : $list")

        return list
            .map {
                Mapper.toCommunityAlgorithmBusiness(it).copy(algorithm = LanguageCenterConstant.ALGORITHM_D2)
            }
    }

    override fun getAlgorithmE1(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityMyBirthDate: Long?
    ): List<CommunityBusiness> {
        val ageLessThan = getCommunityUsers
            .filter {
                if (it.birthDate != null && getCommunityMyBirthDate != null) {
                    it.birthDate < getCommunityMyBirthDate
                } else {
                    false
                }
            }

        val algorithmQty = ratioAlgorithm
            .single { it.algorithmName == LanguageCenterConstant.ALGORITHM_E1 }
            .algorithmQty

        val list = mutableListOf<CommunityUsersDb>()
        while (list.size < algorithmQty) {
            list.add(ageLessThan.random())
        }

        println("getAlgorithmE1 ${list.size} : $list")

        return list
            .map {
                Mapper.toCommunityAlgorithmBusiness(it).copy(algorithm = LanguageCenterConstant.ALGORITHM_E1)
            }
    }

    override fun getAlgorithmE2(
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityMyBirthDate: Long?
    ): List<CommunityBusiness> {
        val ageGreater = getCommunityUsers
            .filter {
                if (it.birthDate != null && getCommunityMyBirthDate != null) {
                    it.birthDate > getCommunityMyBirthDate
                } else {
                    false
                }
            }

        val algorithmQty = ratioAlgorithm
            .single { it.algorithmName == LanguageCenterConstant.ALGORITHM_E2 }
            .algorithmQty

        val list = mutableListOf<CommunityUsersDb>()
        while (list.size < algorithmQty) {
            list.add(ageGreater.random())
        }

        println("getAlgorithmE2 ${list.size} : $list")

        return list
            .map {
                Mapper.toCommunityAlgorithmBusiness(it).copy(algorithm = LanguageCenterConstant.ALGORITHM_E2)
            }
    }

    override fun getAlgorithmF(
        userId: String,
        ratioAlgorithm: List<CommunityAlgorithm>,
        getCommunityUsers: List<CommunityUsersDb>,
        getCommunityUserLocales: List<CommunityUserLocalesDb>,
    ): List<CommunityBusiness> {
        val localeMeTh = getCommunityUserLocales
            .singleOrNull {
                it.userId == userId
                        && it.localeType == LanguageCenterConstant.LOCALE_NATIVE
                        && it.locale == LanguageCenterConstant.LOCALE_THAI
            }

        val localeMeEn = getCommunityUserLocales
            .singleOrNull {
                it.userId == userId
                        && it.localeType == LanguageCenterConstant.LOCALE_NATIVE
                        && it.locale == LanguageCenterConstant.LOCALE_ENGLISH
            }

        val localeNatives = mutableListOf<CommunityUserLocalesDb>()

        when {
            localeMeTh != null -> {
                getCommunityUserLocales
                    .filter {
                        it.userId != userId
                                && it.localeType == LanguageCenterConstant.LOCALE_NATIVE
                                && it.locale == LanguageCenterConstant.LOCALE_THAI
                    }
                    .onEach {
                        localeNatives.add(it)
                    }
            }
            localeMeEn != null -> {
                getCommunityUserLocales
                    .filter {
                        it.userId != userId
                                && it.localeType == LanguageCenterConstant.LOCALE_NATIVE
                                && it.locale == LanguageCenterConstant.LOCALE_ENGLISH
                    }
                    .onEach {
                        localeNatives.add(it)
                    }
            }
        }

        val algorithmQty = ratioAlgorithm.single { it.algorithmName == LanguageCenterConstant.ALGORITHM_F }.algorithmQty

        val list = mutableListOf<CommunityUsersDb>()
        while (list.size < algorithmQty) {
            val userIdLocaleNative = localeNatives.random().userId
            getCommunityUsers.singleOrNull { it.userId == userIdLocaleNative }?.let { list.add(it) }
        }

        println("getAlgorithmF ${list.size} : $list")

        return list
            .map {
                Mapper.toCommunityAlgorithmBusiness(it).copy(algorithm = LanguageCenterConstant.ALGORITHM_F)
            }
    }

    override fun randomCommunities(communities: List<CommunityBusiness>): List<CommunityBusiness> {
        val temp = communities as MutableList
        val list = mutableListOf<CommunityBusiness>()
        while (temp.isNotEmpty()) {
            val rnd = temp.random()
            list.add(rnd)
            temp.remove(rnd)
        }

        return list
//            .distinctBy { it.userId }
//            .take(LanguageCenterConstant.COMMUNITY_LIST_MAX)
    }

    override fun mapToCommunities(
        randomCommunities: List<CommunityBusiness>,
        userLocaleCommunity: List<CommunityUserLocalesDb>
    ): List<Community> {
        val userInfoCommunity = randomCommunities.map { userInfo ->
            Community(
                userId = userInfo.userId,
                email = userInfo.email,
                givenName = userInfo.givenName?.capitalize(),
                familyName = userInfo.familyName?.capitalize(),
                name = userInfo.name?.capitalize(),
                picture = userInfo.picture,
                gender = userInfo.gender,
                age = userInfo.birthDate?.let { getAgeInt(it) },
                birthDateString = convertDateTimeLongToString(userInfo.birthDate),
                birthDateLong = userInfo.birthDate,
                verifiedEmail = userInfo.verifiedEmail,
                aboutMe = userInfo.aboutMe,
                created = convertDateTimeLongToString(userInfo.created),
                updated = convertDateTimeLongToString(userInfo.updated),
                algorithm = userInfo.algorithm,
            )
        }

        val communities = mutableListOf<Community>()
        userInfoCommunity.forEach { userInfo ->
            val userLocaleNativeList = mutableListOf<UserInfoLocale>()
            val userLocaleLearningList = mutableListOf<UserInfoLocale>()

            userLocaleCommunity.filter { userLocale ->
                userLocale.userId == userInfo.userId
            }.filter { userLocale ->
                userLocale.localeType == LanguageCenterConstant.LOCALE_NATIVE
            }.forEach { userLocale ->
                userLocaleNativeList.add(
                    UserInfoLocale(
                        locale = userLocale.locale,
                        level = userLocale.level
                    )
                )
            }

            userLocaleCommunity.filter { userLocale ->
                userLocale.userId == userInfo.userId
            }.filter { userLocale ->
                userLocale.localeType == LanguageCenterConstant.LOCALE_LEARNING
            }.forEach { userLocale ->
                userLocaleLearningList.add(
                    UserInfoLocale(
                        locale = userLocale.locale,
                        level = userLocale.level
                    )
                )
            }

            communities.add(
                userInfo.copy(
                    localNatives = userLocaleNativeList,
                    localLearnings = userLocaleLearningList,
                )
            )
        }

        return communities
    }

    override fun isCreatedLessThenThreeDay(date: Long): Boolean {
        val threeDay = 36_000_00 * 24 * 3
        return (date + threeDay) > System.currentTimeMillis()
    }

}
