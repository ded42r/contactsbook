package com.example.contactsbook.exceptions

class ContactNotFoundException(contactId: Int) : Exception() {
    val details: Map<String, Any> = mapOf("id" to contactId)
    override val message: String
        get() {
            return "Contact not found"
        }
}