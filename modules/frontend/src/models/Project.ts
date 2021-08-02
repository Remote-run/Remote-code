import { ContainerStatus } from '@/models/ContainerStatus'
import { Template } from '@/models/Template'

export class Project {
  public id: number;
  public containerTemplate: Template
  public containerStatus: ContainerStatus;
  public lastRegisteredLogon: string;
  public accessesKey: string;
  public containerName: string;

  constructor (id: number, containerTemplate: Template, containerStatus: ContainerStatus, lastRegisteredLogon: string, accessesKey: string, containerName: string) {
    this.id = id
    this.containerTemplate = containerTemplate
    this.containerStatus = containerStatus
    this.lastRegisteredLogon = lastRegisteredLogon
    this.accessesKey = accessesKey
    this.containerName = containerName
  }
}
