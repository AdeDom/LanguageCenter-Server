package business.business

import com.lc.server.business.business.ServerBusiness
import com.lc.server.business.business.ServerBusinessImpl
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ServerBusinessImplTest {

    private lateinit var business: ServerBusiness

    @Before
    fun setup() {
        business = ServerBusinessImpl()
    }

    @Test
    fun convertDateTimeLongToString() {
        // given
        val birthDate = 783497367581

        // when
        val result = business.convertDateTimeLongToString(birthDate)

        // then
        assertEquals("30/10/2537", result)
    }

    @Test
    fun isCreatedLessThenThreeDay_overThreeDay_returnFalse() {
        // given
        val threeDay = 36_000_00 * 24 * 3
        val today = 1610122535867 - threeDay

        // when
        val result = business.isCreatedLessThenThreeDay(today)

        // then
        assertFalse(result)
    }

    @Test
    fun isCreatedLessThenThreeDay_lessThenThreeDay_returnTrue() {
        // given
        val today = System.currentTimeMillis()

        // when
        val result = business.isCreatedLessThenThreeDay(today)

        // then
        assertTrue(result)
    }

}
