package me.androidbox.data.worker_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.androidbox.data.R
import me.androidbox.data.remote.model.response.EventDto
import me.androidbox.data.remote.model.response.PhotoDto
import me.androidbox.data.remote.network.event.EventService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.data.worker_manager.UploadEventImp.Companion.ERROR
import me.androidbox.data.worker_manager.UploadEventImp.Companion.EVENT_PHOTOS
import me.androidbox.data.worker_manager.util.CreatePhotoMultipart
import okhttp3.MultipartBody

@HiltWorker
class UploadEventWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val eventService: EventService,
    private val createPhotoMultipart: CreatePhotoMultipart,
    private val moshi: Moshi
) : CoroutineWorker(context, workerParameters)  {

    override suspend fun doWork(): Result {
        val listOfPhoto = inputData.getStringArray(UploadEventImp.EVENT_PHOTOS)?.toList()
        val eventRequestJson = inputData.getString(UploadEventImp.EVENT)
        val isEditMode = inputData.getBoolean(UploadEventImp.IS_EDIT_MODE, false)

        val formData = getFormData(isEditMode)

        if(!eventRequestJson.isNullOrEmpty()) {
            val listOfPhotoMultiPart = listOfPhoto?.let {
                /* Create the multipart for the list of photos */
                createPhotoMultipart.createMultipartPhotos(listOfPhoto)
            } ?: listOf()

            val responseResult = checkResult {
                eventService.createEvent(
                    listOfPhoto = listOfPhotoMultiPart,
                    eventBody = MultipartBody.Part.createFormData(formData, eventRequestJson)
                )
            }

            val result = responseResult.fold(
                onSuccess = { eventDto ->
                    if(eventDto.photos.isNotEmpty()) {
                        val outputData = photoToJson(eventDto)

                        Result.success(outputData)
                    }
                    else {
                        Result.success()
                    }
                },
                onFailure = { throwable ->
                    val errorDate = workDataOf(ERROR to throwable)
                    Result.failure(errorDate)
                }
            )

            return result
        }
        else {
            val errorDate = workDataOf(ERROR to context.getString(R.string.request_json_error))
            return Result.failure(errorDate)
        }
    }

    private fun photoToJson(eventDto: EventDto): Data {
        val jsonAdapter =
            moshi.adapter<List<PhotoDto>>(
                Types.newParameterizedType(
                    List::class.java,
                    PhotoDto::class.java
                )
            )
        val photoJson = jsonAdapter.toJson(eventDto.photos)

        return workDataOf(EVENT_PHOTOS to photoJson)
    }

    private fun getFormData(isEditMode: Boolean): String {
        val formData = if (isEditMode) {
            context.getString(R.string.update_event_request)
        }
        else {
            context.getString(R.string.create_event_request)
        }

        return formData
    }
}