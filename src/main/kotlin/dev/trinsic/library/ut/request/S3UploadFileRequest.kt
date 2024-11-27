package dev.trinsic.library.ut.request

import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.InlineDataPart
import com.github.kittinunf.fuel.httpUpload
import dev.trinsic.library.ut.APIResponse
import dev.trinsic.library.ut.response.FailedResponse
import dev.trinsic.library.ut.response.UploadFilesResponse
import dev.trinsic.library.ut.response.UploadSuccessResponse
import java.io.File

class S3UploadFileRequest(private val file: File, private val presignedRequest: UploadFilesResponse) {

    fun execute(res: (APIResponse) -> Unit) {
        val fields = presignedRequest.fields

        val (_, response, result) = presignedRequest.url.httpUpload()
            .add(
                InlineDataPart(name = "acl", content = fields["acl"]!!),
                InlineDataPart(name = "Content-Type", content = fields["Content-Type"]!!),
                InlineDataPart(name = "Content-Disposition", content = fields["Content-Disposition"]!!),
                InlineDataPart(name = "bucket", content = fields["bucket"]!!),
                InlineDataPart(name = "X-Amz-Algorithm", content = fields["X-Amz-Algorithm"]!!),
                InlineDataPart(name = "X-Amz-Credential", content = fields["X-Amz-Credential"]!!),
                InlineDataPart(name = "X-Amz-Date", content = fields["X-Amz-Date"]!!),
                InlineDataPart(name = "X-Amz-Security-Token", content = fields["X-Amz-Security-Token"]!!),
                InlineDataPart(name = "key", content = fields["key"]!!),
                InlineDataPart(name = "Policy", content = fields["Policy"]!!),
                InlineDataPart(name = "X-Amz-Signature", content = fields["X-Amz-Signature"]!!),
                FileDataPart(file, name = "file")
            ).response()

        val responseString = response.data.toString(Charsets.UTF_8)
        result.fold(
            success = {
                res.invoke(UploadSuccessResponse(""))
            },
            failure = {
                res.invoke(FailedResponse(responseString))
            }
        )
    }

}