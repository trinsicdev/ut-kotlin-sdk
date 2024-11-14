package net.playranked.library.ut.response

import net.playranked.library.ut.APIResponse

class UploadFilesResponse(
    val url: String,
    val fields: Map<String, String>,
    val fileUrl: String
) : APIResponse