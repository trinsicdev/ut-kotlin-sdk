package dev.trinsic.library.ut

import com.google.gson.Gson
import dev.trinsic.library.ut.request.S3UploadFileRequest
import dev.trinsic.library.ut.request.UploadFilesRequest
import dev.trinsic.library.ut.response.FailedResponse
import dev.trinsic.library.ut.response.UploadFilesResponse
import dev.trinsic.library.ut.response.UploadSuccessResponse
import java.io.File

class UploadThing(private val apiKey: String) {

    fun uploadFile(file: File, success: (UploadSuccessResponse) -> Unit, failure: (FailedResponse) -> Unit) {
        UploadFilesRequest(apiKey, file).execute {
            if (it is FailedResponse) {
                failure.invoke(it)
                return@execute
            }

            val presignedRequest = it as UploadFilesResponse
            S3UploadFileRequest(file, presignedRequest).execute {
                if (it is FailedResponse) {
                    failure.invoke(it)
                    return@execute
                }

                success.invoke(UploadSuccessResponse(presignedRequest.fileUrl))
            }
        }
    }

    companion object {
        val BASE_URL = "https://api.uploadthing.com/v6/"
        val GSON = Gson()
    }

}