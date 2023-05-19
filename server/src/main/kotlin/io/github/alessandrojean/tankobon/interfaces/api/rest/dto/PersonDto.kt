package io.github.alessandrojean.tankobon.interfaces.api.rest.dto

import io.github.alessandrojean.tankobon.domain.model.Person
import io.github.alessandrojean.tankobon.infrastructure.validation.NullOrNotBlank
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.URL
import org.hibernate.validator.constraints.UUID

data class PersonEntityDto(
  override val id: String,
  override val attributes: PersonAttributesDto,
  override var relationships: List<RelationDto<ReferenceExpansionPerson>>? = null,
) : EntityDto<ReferenceExpansionPerson> {
  @Schema(type = "string", allowableValues = ["PERSON"])
  override val type = EntityType.PERSON
}

data class PersonAttributesDto(
  val name: String,
  val description: String,
  val links: PersonLinksDto,
) : EntityAttributesDto()

data class PersonLinksDto(
  @get:NullOrNotBlank
  @get:URL
  val website: String? = null,
  @get:NullOrNotBlank
  @get:URL(host = "twitter.com")
  val twitter: String? = null,
  @get:NullOrNotBlank
  @get:URL(host = "instagram.com")
  val instagram: String? = null,
  @get:NullOrNotBlank
  @get:URL(host = "facebook.com")
  val facebook: String? = null,
  @get:NullOrNotBlank
  @get:URL(host = "pixiv.net")
  val pixiv: String? = null,
  @get:NullOrNotBlank
  @get:URL(host = "deviantart.com")
  val deviantArt: String? = null,
  @get:NullOrNotBlank
  @get:URL(host = "youtube.com")
  val youTube: String? = null,
)

enum class ReferenceExpansionPerson : ReferenceExpansionEnum {
  LIBRARY,
  PERSON_PICTURE,
}

fun Person.toDto(libraryAttributesDto: LibraryAttributesDto? = null) = PersonEntityDto(
  id = id,
  attributes = toAttributesDto(),
  relationships = listOf(
    RelationDto(
      id = libraryId,
      type = ReferenceExpansionPerson.LIBRARY,
      attributes = libraryAttributesDto,
    )
  )
)

fun Person.toAttributesDto() = PersonAttributesDto(
  name = name,
  description = description,
  links = PersonLinksDto(
    website = links.website,
    twitter = links.twitter,
    instagram = links.instagram,
    facebook = links.facebook,
    pixiv = links.pixiv,
    deviantArt = links.deviantArt,
    youTube = links.youTube,
  )
)

data class PersonCreationDto(
  @get:NotBlank val name: String,
  val description: String,
  @get:NotNull
  val links: PersonLinksDto,
  @get:UUID(version = [4])
  @get:Schema(format = "uuid")
  val library: String,
)

data class PersonUpdateDto(
  @get:NotBlank val name: String,
  val description: String,
  @get:NotNull
  val links: PersonLinksDto,
)
