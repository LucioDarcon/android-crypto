package com.ldss.binary.protector

import com.ldss.binary.protector.crypto.CryptoLocalTest
import com.ldss.binary.protector.shared_preferences.SharedPreferencesManagerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Suíte de testes instrumentados (Android Device / Emulator).
 * Executa todos os testes do KeyStore, SharedPreferences e Contexto.
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    ExampleInstrumentedTest::class,
    CryptoLocalTest::class,
    SharedPreferencesManagerTest::class,
)
class AndroidTestSuite
