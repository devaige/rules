package dev.aige.rules.provider.blackmatrix

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixPath

fun MutableSet<ClashRule>.addBlackMatrixFileRules(path: BlackMatrixPath) = addAll(BlackMatrixFile(path.path).rules)