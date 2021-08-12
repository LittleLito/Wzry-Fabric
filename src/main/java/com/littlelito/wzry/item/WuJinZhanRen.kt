package com.littlelito.wzry.item

class WuJinZhanRen: WzrySwordItem(
    WzryWeaponMaterials().WUJINZHANREN, Settings(),
    0.2F,
    0F,
    0,
    0F) {

    fun passiveSkill(critEffect: Float): Float {
        return critEffect + 0.4F
    }
}

