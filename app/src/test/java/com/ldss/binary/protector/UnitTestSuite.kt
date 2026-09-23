package com.ldss.binary.protector

import com.ldss.binary.protector.viewmodels.LoginViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Suíte de testes unitários locais (JVM).
 * Executa todas as classes de teste unitário em conjunto.
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    ExampleUnitTest::class,
    LoginViewModelTest::class,
)
class UnitTestSuite
