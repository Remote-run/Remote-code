import axios from 'axios'
import { AuthService } from '@/services/AuthService'

const BASE_URL = process.env.VUE_APP_SERVER_URL

const apiConfig = {
  baseURL: 'http://' + BASE_URL
}

/// --- AUTH ENDPOINTS --- ///
export function login (username: string, password: string) {
  return axios({
    ...apiConfig,
    url: '/api/auth/authentication/login',
    method: 'POST',
    data: { userName: username, password: password }
  }
  )
}

/// --- CURRENT PROJECT ENDPOINTS --- ///
export function getCurrentProjects () {
  console.log({ Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null })
  return axios({
    ...apiConfig,
    url: '/api/app/projects',
    method: 'GET',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function deleteProject (projectID: string) {
  return axios({
    ...apiConfig,
    url: '/api/app/projects/' + projectID,
    method: 'DELETE',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function changeProjectPass (projectID: number, newPassword: string) {
  return axios({
    ...apiConfig,
    url: '/api/app/projects/' + projectID,
    method: 'PATCH',
    data: newPassword,
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function initializeTemplateToProject (templateID: string) {
  return axios({
    ...apiConfig,
    url: '/api/app/projects/new/' + templateID,
    method: 'GET',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

/// --- TEMPLATES ENDPOINTS --- ///

export function deleteTemplate (templateID: string) {
  return axios({
    ...apiConfig,
    url: '/api/app/templates/' + templateID,
    method: 'DELETE',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function getCurrentTemplates () {
  return axios({
    ...apiConfig,
    url: '/api/app/templates',
    method: 'GET',
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}

export function AddNewTemplate (templateName: string, githubLink: string, dockerBuildSteps: string[]) {
  return axios({
    ...apiConfig,
    url: '/api/app/templates/new',
    method: 'post',
    data: { templateName: templateName, githubLink: githubLink, dockerBuildSteps: dockerBuildSteps },
    headers: { Authorization: AuthService.isLoggedIn() ? AuthService.getToken() : null }
  }
  )
}
