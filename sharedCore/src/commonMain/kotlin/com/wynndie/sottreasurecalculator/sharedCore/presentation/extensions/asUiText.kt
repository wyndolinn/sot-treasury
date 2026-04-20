package com.wynndie.sottreasurecalculator.sharedCore.presentation.extensions

import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.DataError
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.Error
import com.wynndie.sottreasurecalculator.sharedCore.domain.outcome.ValidationError
import com.wynndie.sottreasurecalculator.sharedCore.presentation.formatters.UiText
import com.wynndie.sottreasurecalculator.sharedCore.presentation.formatters.UiText.ResourceString
import com.wynndie.sottreasurecalculator.sharedResources.Res.string
import com.wynndie.sottreasurecalculator.sharedResources.error_above_maximum_length
import com.wynndie.sottreasurecalculator.sharedResources.error_above_maximum_value
import com.wynndie.sottreasurecalculator.sharedResources.error_bad_request
import com.wynndie.sottreasurecalculator.sharedResources.error_below_minimum_length
import com.wynndie.sottreasurecalculator.sharedResources.error_below_minimum_value
import com.wynndie.sottreasurecalculator.sharedResources.error_disk_full
import com.wynndie.sottreasurecalculator.sharedResources.error_empty_field
import com.wynndie.sottreasurecalculator.sharedResources.error_exact_length_required
import com.wynndie.sottreasurecalculator.sharedResources.error_exact_value_required
import com.wynndie.sottreasurecalculator.sharedResources.error_forbidden
import com.wynndie.sottreasurecalculator.sharedResources.error_internal_server_error
import com.wynndie.sottreasurecalculator.sharedResources.error_invalid_characters
import com.wynndie.sottreasurecalculator.sharedResources.error_invalid_format
import com.wynndie.sottreasurecalculator.sharedResources.error_no_internet
import com.wynndie.sottreasurecalculator.sharedResources.error_not_found
import com.wynndie.sottreasurecalculator.sharedResources.error_request_timeout
import com.wynndie.sottreasurecalculator.sharedResources.error_serialization
import com.wynndie.sottreasurecalculator.sharedResources.error_too_many_requests
import com.wynndie.sottreasurecalculator.sharedResources.error_unauthorized
import com.wynndie.sottreasurecalculator.sharedResources.error_unknown


fun Error.asUiText(): UiText {
    return when (this) {
        DataError.Remote.BAD_REQUEST -> ResourceString(string.error_bad_request)
        DataError.Remote.UNAUTHORIZED -> ResourceString(string.error_unauthorized)
        DataError.Remote.FORBIDDEN -> ResourceString(string.error_forbidden)
        DataError.Remote.NOT_FOUND -> ResourceString(string.error_not_found)
        DataError.Remote.REQUEST_TIMEOUT -> ResourceString(string.error_request_timeout)
        DataError.Remote.TOO_MANY_REQUESTS -> ResourceString(string.error_too_many_requests)
        DataError.Remote.SERVER_ERROR -> ResourceString(string.error_internal_server_error)
        DataError.Remote.NO_INTERNET -> ResourceString(string.error_no_internet)
        DataError.Remote.SERIALIZATION_ERROR -> ResourceString(string.error_serialization)
        DataError.Remote.UNKNOWN -> ResourceString(string.error_unknown)

        DataError.Local.DISK_FULL -> ResourceString(string.error_disk_full)

        ValidationError.EMPTY_FIELD -> ResourceString(string.error_empty_field)
        ValidationError.BELOW_MINIMUM_VALUE -> ResourceString(string.error_below_minimum_value)
        ValidationError.ABOVE_MAXIMUM_VALUE -> ResourceString(string.error_above_maximum_value)
        ValidationError.EXACT_VALUE_REQUIRED -> ResourceString(string.error_exact_value_required)
        ValidationError.INVALID_CHARACTERS -> ResourceString(string.error_invalid_characters)
        ValidationError.INVALID_FORMAT -> ResourceString(string.error_invalid_format)
        ValidationError.BELOW_MINIMUM_LENGTH -> ResourceString(string.error_below_minimum_length)
        ValidationError.ABOVE_MAXIMUM_LENGTH -> ResourceString(string.error_above_maximum_length)
        ValidationError.EXACT_LENGTH_REQUIRED -> ResourceString(string.error_exact_length_required)
    }
}