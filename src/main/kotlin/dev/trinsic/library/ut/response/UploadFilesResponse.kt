package dev.trinsic.library.ut.response

import dev.trinsic.library.ut.APIResponse

class UploadFilesResponse(
    val url: String,
    val fields: Map<String, String>,
    val fileUrl: String
) : APIResponse