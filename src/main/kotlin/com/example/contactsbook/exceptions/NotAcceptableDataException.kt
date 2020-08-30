package com.example.contactsbook.exceptions

class NotAcceptableDataException(
        private val validatableClass: Any,
        val details: Map<String, Any>) : Exception() {
    override val message: String
        get() {
            return "Validation error for ${validatableClass.javaClass.simpleName}"
        }
}
