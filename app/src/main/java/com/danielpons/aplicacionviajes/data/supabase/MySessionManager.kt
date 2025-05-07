package com.danielpons.aplicacionviajes.data.supabase

import io.github.jan.supabase.auth.SessionManager
import io.github.jan.supabase.auth.user.UserSession

object MySessionManager : SessionManager {
    // Usamos un objeto UserSession para manejar los datos de sesión
    private var session: UserSession? = null


    // Elimina la sesión de forma asincrónica (puedes añadir lógica de almacenamiento si lo deseas)
    override suspend fun deleteSession() {
        session = null
        // Aquí puedes agregar código adicional para eliminar la sesión de un almacenamiento persistente, si es necesario
    }

    // Carga la sesión guardada de forma asincrónica
    override suspend fun loadSession(): UserSession? {
        // Aquí puedes cargar la sesión desde almacenamiento persistente, si lo tienes
        return session
    }

    // Guarda la sesión de forma asincrónica
    override suspend fun saveSession(session: UserSession) {
        this.session = session
        // Aquí puedes agregar código adicional para guardar la sesión en almacenamiento persistente
    }

      fun getSession(): UserSession? {
        return session
    }
    fun setSession(userSession: UserSession) {
        session = userSession
    }
    fun clearSession() {
        session = null
    }
}
