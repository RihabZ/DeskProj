package com.rihab.interventions.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rihab.interventions.dto.EquipementDTO;
import com.rihab.interventions.entities.*;
import com.rihab.interventions.repos.*;

@Service
public class EquipementServiceImpl implements EquipementService {
	
				@Autowired
				EquipementRepository equipementRepository;
				@Autowired
				ImageService imageService;
				
				
				
				@Override
				public EquipementDTO save(EquipementDTO equipementDTO, MultipartFile imageFile) {
				    try {
				        Equipement equipement = convertToEntity(equipementDTO);

				        // Gérer l'image
				        if (imageFile != null && !imageFile.isEmpty()) {
				            Image newImage = imageService.uploadImage(imageFile);
				            if (equipement.getImage() != null) {
				                imageService.deleteImage(equipement.getImage().getIdImage());
				            }
				            equipement.setImage(newImage);
				        }

				        // Vérifiez que l'identifiant est bien défini
				        if (equipement.getEqptCode() == null || equipement.getEqptCode().isEmpty()) {
				            equipement.setEqptCode(equipement.generateEqptCodeBasedOnDesignation());
				        }

				        Equipement savedEquipement = equipementRepository.save(equipement);
				        return convertToDTO(savedEquipement);
				    } catch (Exception e) {
				        e.printStackTrace();
				        throw new RuntimeException("Error saving equipement", e);
				    }
				}

				public Equipement convertToEntity(EquipementDTO equipementDTO) {
				    if (equipementDTO == null) {
				        throw new IllegalArgumentException("EquipementDTO cannot be null");
				    }

				    Equipement equipement = new Equipement();

				    if (equipementDTO.getEqptDesignation() == null || equipementDTO.getEqptDesignation().isEmpty()) {
				        throw new IllegalArgumentException("Designation cannot be null or empty");
				    }

				    equipement.setEqptDesignation(equipementDTO.getEqptDesignation());
				    equipement.setEqptCode(equipementDTO.getCode() != null ? equipementDTO.getCode() : equipement.generateEqptCodeBasedOnDesignation());

				    // Autres champs...
				    equipement.setEqptId(equipementDTO.getEqptId());
				    equipement.setEqptPrix(equipementDTO.getEqptPrix());
				    equipement.setEqptDtAchat(equipementDTO.getEqptDtAchat());
				    equipement.setEqptGarantie(equipementDTO.getEqptGarantie());
				    equipement.setEqptEnService(equipementDTO.getEqptEnService());
				    equipement.setEqptGarTypeDtRef(equipementDTO.getEqptGarTypeDtRef());
				    equipement.setEqptMachine(equipementDTO.getEqptMachine());
				    equipement.setEqptDtCreation(equipementDTO.getEqptDtCreation());
				    equipement.setEqptDureeGarantie(equipementDTO.getEqptDureeGarantie());
				    equipement.setDateFinGarantie(equipementDTO.getDateFinGarantie());
				    equipement.setSiteCode(equipementDTO.getSiteCode());
				    equipement.setCentreCode(equipementDTO.getCentreCode());
				    equipement.setArticleCode(equipementDTO.getArticleCode());
				    equipement.setEqptLotNumero(equipementDTO.getEqptLotNumero());
				    equipement.setEqptNumeroSerie(equipementDTO.getEqptNumeroSerie());
				    equipement.setDateFabrication(equipementDTO.getDateFabrication());
				    equipement.setDateInstallation(equipementDTO.getDateInstallation());
				    equipement.setDateMiseEnService(equipementDTO.getDateMiseEnService());
				    equipement.setPostCode(equipementDTO.getPostCode());
				    equipement.setRessCode(equipementDTO.getRessCode());
				    equipement.setDateDemontage(equipementDTO.getDateDemontage());
				    equipement.setDateRemplacement(equipementDTO.getDateRemplacement());
				    equipement.setDateLivraison(equipementDTO.getDateLivraison());
				    equipement.setType(equipementDTO.getType());
				    equipement.setClient(equipementDTO.getClient());
				    equipement.setFamille(equipementDTO.getFamille());

				    // Gestion de l'image
				    if (equipementDTO.getImageId() != null && equipementDTO.getImagePath() != null) {
				        Image image = new Image();
				        image.setIdImage(equipementDTO.getImageId());
				        equipement.setImage(image);
				    }

				    return equipement;
				}

				    public EquipementDTO convertToDTO(Equipement equipement) {
				        if (equipement == null) {
				            return null;
				        }

				        return EquipementDTO.builder()
				                .code(equipement.getEqptCode())
				                .eqptDesignation(equipement.getEqptDesignation())
				                .eqptId(equipement.getEqptId())
				                .eqptPrix(equipement.getEqptPrix())
				                .eqptDtAchat(equipement.getEqptDtAchat())
				                .eqptGarantie(equipement.getEqptGarantie())
				                .eqptEnService(equipement.getEqptEnService())
				                .eqptGarTypeDtRef(equipement.getEqptGarTypeDtRef())
				                .eqptMachine(equipement.getEqptMachine())
				                .eqptDtCreation(equipement.getEqptDtCreation())
				                .eqptDureeGarantie(equipement.getEqptDureeGarantie())
				                .dateFinGarantie(equipement.getDateFinGarantie())
				                .siteCode(equipement.getSiteCode())
				                .centreCode(equipement.getCentreCode())
				                .articleCode(equipement.getArticleCode())
				                .eqptLotNumero(equipement.getEqptLotNumero())
				                .eqptNumeroSerie(equipement.getEqptNumeroSerie())
				                .dateFabrication(equipement.getDateFabrication())
				                .dateInstallation(equipement.getDateInstallation())
				                .dateMiseEnService(equipement.getDateMiseEnService())
				                .postCode(equipement.getPostCode())
				                .ressCode(equipement.getRessCode())
				                .dateDemontage(equipement.getDateDemontage())
				                .dateRemplacement(equipement.getDateRemplacement())
				                .dateLivraison(equipement.getDateLivraison())
				                .type(equipement.getType())
				                .client(equipement.getClient())
				                .famille(equipement.getFamille())
				                .imageId(equipement.getImage() != null ? equipement.getImage().getIdImage() : null)
				                .build();
				    }
	
				    public Equipement updateEquipement(String equipementId, EquipementDTO equipementDTO, MultipartFile imageFile) {
				        try {
				            // Rechercher l'équipement à mettre à jour en fonction de l'ID fourni
				            Equipement existingEquipement = equipementRepository.findById(equipementId)
				                    .orElseThrow(() -> new IllegalArgumentException("Equipement not found"));

				            // Mise à jour des champs de l'équipement existant
				            existingEquipement.setEqptDesignation(equipementDTO.getEqptDesignation());
				            existingEquipement.setEqptPrix(equipementDTO.getEqptPrix());
				            existingEquipement.setEqptDtAchat(equipementDTO.getEqptDtAchat());
				            existingEquipement.setEqptGarantie(equipementDTO.getEqptGarantie());
				            existingEquipement.setEqptEnService(equipementDTO.getEqptEnService());
				            existingEquipement.setEqptGarTypeDtRef(equipementDTO.getEqptGarTypeDtRef());
				            existingEquipement.setEqptMachine(equipementDTO.getEqptMachine());
				            existingEquipement.setEqptDtCreation(equipementDTO.getEqptDtCreation());
				            existingEquipement.setEqptDureeGarantie(equipementDTO.getEqptDureeGarantie());
				            existingEquipement.setDateFinGarantie(equipementDTO.getDateFinGarantie());
				            existingEquipement.setSiteCode(equipementDTO.getSiteCode());
				            existingEquipement.setCentreCode(equipementDTO.getCentreCode());
				            existingEquipement.setArticleCode(equipementDTO.getArticleCode());
				            existingEquipement.setEqptLotNumero(equipementDTO.getEqptLotNumero());
				            existingEquipement.setEqptNumeroSerie(equipementDTO.getEqptNumeroSerie());
				            existingEquipement.setDateFabrication(equipementDTO.getDateFabrication());
				            existingEquipement.setDateInstallation(equipementDTO.getDateInstallation());
				            existingEquipement.setDateMiseEnService(equipementDTO.getDateMiseEnService());
				            existingEquipement.setPostCode(equipementDTO.getPostCode());
				            existingEquipement.setRessCode(equipementDTO.getRessCode());
				            existingEquipement.setDateDemontage(equipementDTO.getDateDemontage());
				            existingEquipement.setDateRemplacement(equipementDTO.getDateRemplacement());
				            existingEquipement.setDateLivraison(equipementDTO.getDateLivraison());
				            existingEquipement.setType(equipementDTO.getType());
				            existingEquipement.setClient(equipementDTO.getClient());
				            existingEquipement.setFamille(equipementDTO.getFamille());

				            // Gestion de l'image
				            if (imageFile != null && !imageFile.isEmpty()) {
				                Image newImage = imageService.uploadImage(imageFile);
				                if (existingEquipement.getImage() != null) {
				                    imageService.deleteImage(existingEquipement.getImage().getIdImage());
				                }
				                existingEquipement.setImage(newImage);
				            }

				            Equipement updatedEquipement = equipementRepository.save(existingEquipement);
				            return updatedEquipement;
				        } catch (Exception e) {
				            e.printStackTrace();
				            throw new RuntimeException("Error updating equipement", e);
				        }
				    }

	
	@Override
	public void deleteEquipement(Equipement eqpt) {
		equipementRepository.delete(eqpt);
	}
	
	
	 @Override
	public void deleteEquipementByCode(String code) {
		 equipementRepository.deleteById(code);
	}
	 
	 
	@Override
	public Equipement getEquipement(String code) {
			return equipementRepository.findById(code).get();
	}
	
	
	@Override
	public List<Equipement> getAllEquipements() {
			return equipementRepository.findAll();
	}


	
	@Override
	public List<Equipement> findByEqptDesignation(String desing)
	{
		return equipementRepository.findByEqptDesignation(desing);
	}
	@Override
	public List<Equipement> findByEqptDesignationContains(String desing)
	{
		return equipementRepository.findByEqptDesignationContains(desing);
	}

	@Override
	public List<Equipement> findByDesingPrix ( String desing,Double prix)
	{
		return equipementRepository.findByDesingPrix(desing,prix);
	}


	
	@Override
	public List<Equipement> findByTypeEqtyCode(String eqtyCode)
	{
		return equipementRepository.findByTypeEqtyCode( eqtyCode);
				
	}

	


	@Override
	public List<Equipement> findByClientCodeClient(Long codeClient)
	{
		return equipementRepository.findByClientCodeClient( codeClient);
				
	}

	@Override
	public List<EquipementDTO> getAllEquipementsDTOs() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
	
}
