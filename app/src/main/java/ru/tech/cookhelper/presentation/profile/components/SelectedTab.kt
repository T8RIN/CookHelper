package ru.tech.cookhelper.presentation.profile.components

enum class SelectedTab { Posts, Recipes }

fun Int.toTab(): SelectedTab = SelectedTab.values().first { it.ordinal == this }