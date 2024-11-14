package net.playranked.library.ut.request

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.google.gson.JsonObject
import net.playranked.library.ut.APIResponse
import net.playranked.library.ut.UploadThing
import net.playranked.library.ut.response.FailedResponse
import net.playranked.library.ut.response.UploadFilesResponse
import java.io.File
import kotlin.io.path.fileSize

class UploadFilesRequest(private val apiKey: String, private val file: File) {

    private val PATH = "uploadFiles"

    fun execute(res: (APIResponse) -> Unit) {
        val (_, response, result) = (UploadThing.BASE_URL + PATH).httpPost()
            .header("X-Uploadthing-Api-Key", apiKey)
            .jsonBody(UploadThing.GSON.toJson(mapOf(
                "files" to arrayOf(
                    mapOf(
                        "name" to file.name,
                        "type" to file.extension,
                        "size" to file.toPath().fileSize()
                    )
                ),
            ))).response()

        val responseString = response.data.toString(Charsets.UTF_8)
        result.fold(
            success = {
                val jsonObject = UploadThing.GSON.fromJson(responseString, JsonObject::class.java)
                res.invoke(UploadThing.GSON.fromJson(jsonObject["data"].asJsonArray[0], UploadFilesResponse::class.java))
            },
            failure = {
                res.invoke(FailedResponse(responseString))
            }
        )
    }

}