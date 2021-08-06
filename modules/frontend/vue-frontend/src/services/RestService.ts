import axios from 'axios'
import { AuthService } from '@/services/AuthService'

const BASE_URL = process.env.VUE_APP_SERVER_URL

const apiConfig = {
  baseURL: 'https://api.' + BASE_URL
}

/// --- AUTH ENDPOINTS --- ///
export function login (username: string, password: string) {
  return axios({
    ...apiConfig,
    url: '/auth/authentication/login',
    method: 'POST',
    data: { userName: username, password: password }
  }
  )
}

export function createUser (username: string, password: string) {
  return axios({
    ...apiConfig,
    url: '/auth/authentication/newuser',
    params: { username: username, password: password },
    method: 'POST'
  }
  )
}

export function createAdminUser (username: string, password: string) {
  return axios({
    ...apiConfig,
    url: '/auth/authentication/newuser',
    data: { username: username, password: password, groups: ['admin', 'user'] },
    method: 'POST',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

/// --- CURRENT PROJECT ENDPOINTS --- ///
export function getCurrentProjects () {
  return axios({
    ...apiConfig,
    url: '/app/projects',
    method: 'GET',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function deleteProject (projectID: string) {
  return axios({
    ...apiConfig,
    url: '/app/projects/' + projectID,
    method: 'DELETE',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function changeProjectPass (projectID: number, newPassword: string) {
  return axios({
    ...apiConfig,
    url: '/app/projects/' + projectID,
    method: 'PATCH',
    data: newPassword,
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function initializeTemplateToProject (templateID: string) {
  return axios({
    ...apiConfig,
    url: '/app/projects/new/' + templateID,
    method: 'GET',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

/// --- TEMPLATES ENDPOINTS --- ///

export function deleteTemplate (templateID: string) {
  return axios({
    ...apiConfig,
    url: '/app/templates/' + templateID,
    method: 'DELETE',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function getCurrentTemplates () {
  return axios({
    ...apiConfig,
    url: '/app/templates',
    method: 'GET',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function AddNewTemplate (templateName: string, githubLink: string, dockerBuildSteps: string[]) {
  return axios({
    ...apiConfig,
    url: '/app/templates/new',
    method: 'post',
    data: { templateName: templateName, githubLink: githubLink, dockerBuildSteps: dockerBuildSteps },
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}
