import { ContainerStatus } from '@/models/ContainerStatus'
import { Template } from '@/models/Template'

export class Project {
  private _id: number;
  private _containerTemplate: Template
  private _containerStatus: ContainerStatus;
  private _lastRegisteredLogon: string;
  private _accessesKey: string;
  private _projectURL: string;

  constructor (id: number, containerTemplate: Template, containerStatus: ContainerStatus, lastRegisteredLogon: string, accessesKey: string, projectURL: string) {
    this._id = id
    this._containerTemplate = containerTemplate
    this._containerStatus = containerStatus
    this._lastRegisteredLogon = lastRegisteredLogon
    this._accessesKey = accessesKey
    this._projectURL = projectURL
  }

  get id (): number {
    return this._id
  }

  get containerTemplate (): Template {
    return this._containerTemplate
  }

  get containerStatus (): ContainerStatus {
    return this._containerStatus
  }

  set containerStatus (value: ContainerStatus) {
    this._containerStatus = value
  }

  get lastRegisteredLogon (): string {
    return this._lastRegisteredLogon
  }

  get accessesKey (): string {
    return this._accessesKey
  }

  get projectURL (): string {
    return this._projectURL
  }

  set projectURL (value: string) {
    this._projectURL = value
  }
}
