package com.ldss.binary.protector.shared_preferences

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferencesManagerTest {

    private lateinit var prefsManager: SharedPreferencesManager

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        prefsManager = SharedPreferencesManager(context)
        prefsManager.initialize("test_user")
    }

    @Test
    fun testSaveAndReadValue() {
        val key = "minha_chave_teste"
        val expectedValue = "meu_valor_teste"

        prefsManager.save(key, expectedValue)
        val actualValue = prefsManager.getValue(key)

        assertEquals("O valor lido deve corresponder ao valor salvo", expectedValue, actualValue)
    }

    @Test
    fun testRemoveValue() {
        val key = "chave_para_remover"
        val value = "valor_temporario"

        prefsManager.save(key, value)
        assertEquals(value, prefsManager.getValue(key))

        prefsManager.remove(key)
        assertNull("O valor deve ser nulo após a remoção", prefsManager.getValue(key))
    }
}
