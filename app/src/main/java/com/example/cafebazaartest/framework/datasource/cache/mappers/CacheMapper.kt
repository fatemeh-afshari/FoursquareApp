package com.example.cafebazaartest.framework.datasource.cache.mappers

import com.example.cafebazaartest.business.domain.model.Contact
import com.example.cafebazaartest.business.domain.model.Location
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.business.domain.util.EntityMapper
import com.example.cafebazaartest.framework.datasource.cache.model.VenueCacheEntity

class CacheMapper: EntityMapper<VenueCacheEntity, Venue> {
    fun entityListToVenueList(entities: List<VenueCacheEntity>): List<Venue>{
        val list: ArrayList<Venue> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun venueListToEntityList(venues: List<Venue>): List<VenueCacheEntity>{
        val entities: ArrayList<VenueCacheEntity> = ArrayList()
        for(venue in venues){
            entities.add(mapToEntity(venue))
        }
        return entities
    }
    override fun mapFromEntity(entity: VenueCacheEntity): Venue {
        return  Venue(entity.id,entity.name, Contact(entity.phone , ""), Location(entity.address , "" , entity.lat , entity.lon ) )
    }

    override fun mapToEntity(domainModel: Venue): VenueCacheEntity {
       return VenueCacheEntity(domainModel.id , domainModel.name, domainModel.location.lat , domainModel.location.lng ,
           domainModel.contact?.formattedPhone, domainModel.location.address+domainModel.location.crossStreet )
    }
}