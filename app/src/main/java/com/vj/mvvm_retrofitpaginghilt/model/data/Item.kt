package com.vj.mvvm_retrofitpaginghilt.model.data

data class Item(
    val archive_url: String,
    val archived: Boolean,
    val created_at: String,
    val default_branch: String,
    val description: String,
    val disabled: Boolean,
    val fork: Boolean,
    val forks: Int,
    val forks_count: Int,
    val full_name: String,
    val has_downloads: Boolean,
    val has_issues: Boolean,
    val homepage: String,
    val id: Int,
    val language: Any,
    val name: String,
    val open_issues: Int,
    val open_issues_count: Int,
    val owner: Owner,
    val score: Double,
    val stargazers_count: Int,
    val updated_at: String,
    val watchers: Int,
    val watchers_count: Int
)