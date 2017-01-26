package fr.univlille1.m2iagl.durey.start;

import java.util.List;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;

public class StartCheckingCyclicIntoCristalSchema {

	public static void main(String [] args){


		DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		String batiment = "batiment",
				personne = "personne",
				accesbatiment = "accesbatiment",
				action = "action",
				actualite = "actualite",
				categorieactualite = "categorieactualite",
				affectation = "affectation",
				equipe = "equipe",
				support = "support",
				typeaffectation = "typeaffectation",
				assistant = "assistant",
				enseignement = "enseignement",
				site = "site",
				chefequipe = "chefequipe",
				cle = "cle",
				coencadrement = "coencadrement",
				personneexterieur = "personnexterieur",
				dernierdiplome = "dernierdiplome",
				listediplome = "listediplome",
				pays = "pays",
				diplome = "diplome",
				domainediplome = "domainediplome",
				sujet = "sujet",
				etablissement = "etablissement",
				email = "email",
				employeur = "employeur",
				catemployeur = "catemployeur",
				emr = "emr",
				slistediffusion = "s_listediffusion",
				type_equipe = "type_equipe",
				equipeexterne = "equipeexterne",
				labo = "labo",
				grade = "grade",
				catgrade = "catgrade",
				supann_grade = "supann_grade",
				hdr = "hdr",
				matiere = "matiere",
				hebergement = "hebergement",
				hebergeur = "hebergeur",
				inscription = "inscription",
				contact = "contact",
				civilite = "civilite",
				issuede = "issuede",
				listediffusion = "listediffusion",
				listehdr = "listehdr",
				localisation = "localisation",
				publi = "public",
				typepublic = "typepublic",
				ractuequ = "r_actuequ",
				requact = "r_equact",
				requextequ = "r_equextequ",
				requextlab = "r_equextlab",
				requlab = "r_equlab",
				requpla = "r_equpla",
				plateforme = "plateforme",
				requthe = "r_equthe",
				theme = "theme",
				rlabperfon = "r_labperfon",
				fonction = "fonction",
				rperlab = "r_perlab",
				rsupfin = "r_supfin",
				rthelab = "r_thelab",
				responsabiliteplateforme = "responsabilite_plateforme",
				responsablegt = "reponsablegt",
				sacces = "s_acces",
				smodule = "s_module",
				srapport = "s_rapport",
				sdomaine = "s_domaine",
				ssurligneur = "s_surligneur",
				sressource = "s_ressource",
				soutenance = "soutenance",
				lieusoutenance = "lieusoutenance",
				categorie = "categorie",
				cmu = "cmu",
				typefinancement = "typefinancement",
				typesupport = "typesupport",
				section = "section",
				these = "these",
				zoom = "zoom",
				scatzoom = "s_catzoom",
				scouleurzoom = "s_couleurzoom";
		
		graph.addVertex(accesbatiment);
		graph.addVertex(batiment);
		graph.addVertex(personne);
		graph.addVertex(action);
		graph.addVertex(actualite);
		graph.addVertex(categorieactualite);
		graph.addVertex(affectation);
		graph.addVertex(equipe);
		graph.addVertex(support);
		graph.addVertex(assistant);
		graph.addVertex(enseignement);
		graph.addVertex(site);
		graph.addVertex(chefequipe);
		graph.addVertex(cle);
		graph.addVertex(coencadrement);
		graph.addVertex(personneexterieur);
		graph.addVertex(dernierdiplome);
		graph.addVertex(listediplome);
		graph.addVertex(pays);
		graph.addVertex(diplome);
		graph.addVertex(domainediplome);
		graph.addVertex(sujet);
		graph.addVertex(etablissement);
		graph.addVertex(email);
		graph.addVertex(employeur);
		graph.addVertex(catemployeur);
		graph.addVertex(emr);
		graph.addVertex(slistediffusion);
		graph.addVertex(type_equipe);
		graph.addVertex(equipeexterne);
		graph.addVertex(labo);
		graph.addVertex(grade);
		graph.addVertex(catgrade);
		graph.addVertex(supann_grade);
		graph.addVertex(hdr);
		graph.addVertex(matiere);
		graph.addVertex(hebergement);
		graph.addVertex(hebergeur);
		graph.addVertex(inscription);
		graph.addVertex(contact);
		graph.addVertex(civilite);
		graph.addVertex(issuede);
		graph.addVertex(listediffusion);
		graph.addVertex(listehdr);
		graph.addVertex(localisation);
		graph.addVertex(publi);
		graph.addVertex(typepublic);
		graph.addVertex(ractuequ);
		graph.addVertex(requact);
		graph.addVertex(requextequ);
		graph.addVertex(requextlab);
		graph.addVertex(requlab);
		graph.addVertex(requpla);
		graph.addVertex(requthe);
		graph.addVertex(plateforme);
		graph.addVertex(theme);
		graph.addVertex(rlabperfon);
		graph.addVertex(fonction);
		graph.addVertex(rperlab);
		graph.addVertex(rsupfin);
		graph.addVertex(rthelab);
		graph.addVertex(responsabiliteplateforme);
		graph.addVertex(responsablegt);
		graph.addVertex(sacces);
		graph.addVertex(smodule);
		graph.addVertex(srapport);
		graph.addVertex(sdomaine);
		graph.addVertex(ssurligneur);
		graph.addVertex(sressource);
		graph.addVertex(slistediffusion);
		graph.addVertex(soutenance);
		graph.addVertex(lieusoutenance);
		graph.addVertex(categorie);
		graph.addVertex(cmu);
		graph.addVertex(typefinancement);
		graph.addVertex(typesupport);
		graph.addVertex(typeaffectation);
		graph.addVertex(section);
		graph.addVertex(these);
		graph.addVertex(zoom);
		graph.addVertex(scatzoom);
		graph.addVertex(scouleurzoom);

	
		
		graph.addEdge(accesbatiment, batiment);
		graph.addEdge(accesbatiment, personne);
		
		graph.addEdge(action, personne);
		graph.addEdge(actualite, categorieactualite);
		
		graph.addEdge(affectation, equipe);
		graph.addEdge(affectation, support);
		graph.addEdge(affectation, typeaffectation);
		
		graph.addEdge(assistant, enseignement);
		graph.addEdge(assistant, support);
		
		graph.addEdge(batiment, site);
		
		graph.addEdge(chefequipe, equipe);
		graph.addEdge(chefequipe, personne);
		
		graph.addEdge(cle, personne);
		graph.addEdge(coencadrement, personneexterieur);
		graph.addEdge(coencadrement, personne);
		
		graph.addEdge(dernierdiplome, listediplome);
		graph.addEdge(dernierdiplome, pays);
		graph.addEdge(dernierdiplome, personne);
		
		graph.addEdge(diplome, personne);
		graph.addEdge(diplome, personne);
		graph.addEdge(diplome, personneexterieur);
		graph.addEdge(diplome, domainediplome);
		graph.addEdge(diplome, equipe);
		graph.addEdge(diplome, etablissement);
		graph.addEdge(diplome, personne);
		graph.addEdge(diplome, sujet);
		
		graph.addEdge(email, personne);
		
		graph.addEdge(employeur, catemployeur);
		
		graph.addEdge(emr, equipe);
		
		graph.addEdge(equipe, equipe);
		graph.addEdge(equipe, slistediffusion);
		graph.addEdge(equipe, type_equipe);
		
		graph.addEdge(equipeexterne, labo);
		graph.addEdge(equipeexterne, type_equipe);
		
		graph.addEdge(grade, catgrade);
		graph.addEdge(grade, supann_grade);
		
		graph.addEdge(hdr, personne);
		graph.addEdge(hdr, personne);
		graph.addEdge(hdr, personne);
		graph.addEdge(hdr, personneexterieur);
		graph.addEdge(hdr, equipe);
		graph.addEdge(hdr, domainediplome);
		graph.addEdge(hdr, etablissement);
		graph.addEdge(hdr, sujet);
		graph.addEdge(hdr, matiere);
		
		graph.addEdge(hebergement, hebergeur);
		
		graph.addEdge(inscription, batiment);
		graph.addEdge(inscription, civilite);
		graph.addEdge(inscription, contact);
		graph.addEdge(inscription, employeur);
		graph.addEdge(inscription, equipe);
		graph.addEdge(inscription, grade);
		graph.addEdge(inscription, pays);
		
		graph.addEdge(issuede, equipe);
		graph.addEdge(issuede, equipe);
		
		graph.addEdge(labo, listediffusion);
		
		graph.addEdge(listehdr, personne);
		
		graph.addEdge(localisation, batiment);
		graph.addEdge(localisation, personne);

		graph.addEdge(personne, civilite);
		graph.addEdge(personne, labo);
		graph.addEdge(personne, contact);
		graph.addEdge(personne, email);
		graph.addEdge(personne, pays);

		graph.addEdge(personneexterieur, employeur);
		
		graph.addEdge(publi, labo);
		graph.addEdge(publi, typepublic);
		
		graph.addEdge(ractuequ, actualite);
		graph.addEdge(ractuequ, equipe);
		
		graph.addEdge(requact, action);
		graph.addEdge(requact, equipe);
		
		graph.addEdge(requextequ, equipeexterne);
		graph.addEdge(requextequ, equipe);
		
		graph.addEdge(requextlab, equipeexterne);
		graph.addEdge(requextlab, labo);
		
		graph.addEdge(requlab, equipe);
		graph.addEdge(requlab, labo);
		
		graph.addEdge(requpla, equipe);
		graph.addEdge(requpla, plateforme);
		
		graph.addEdge(requthe, equipe);
		graph.addEdge(requthe, theme);
		
		graph.addEdge(rlabperfon, fonction);
		graph.addEdge(rlabperfon, labo);
		graph.addEdge(rlabperfon, personne);
		
		graph.addEdge(rperlab, labo);
		graph.addEdge(rperlab, personne);
		
		graph.addEdge(rsupfin, employeur);
		graph.addEdge(rsupfin, support);
		
		graph.addEdge(rthelab, labo);
		graph.addEdge(rthelab, theme);
		
		graph.addEdge(responsabiliteplateforme, personne);
		graph.addEdge(responsabiliteplateforme, plateforme);
		
		graph.addEdge(responsablegt, theme);
		graph.addEdge(responsablegt, personne);

		graph.addEdge(sacces, personne);
		graph.addEdge(sacces, smodule);
		
		graph.addEdge(srapport, sdomaine);
		graph.addEdge(srapport, ssurligneur);
		
		graph.addEdge(sressource, slistediffusion);
		
		graph.addEdge(soutenance, lieusoutenance);

		graph.addEdge(support, categorie);
		graph.addEdge(support, cmu);
		graph.addEdge(support, employeur);
		graph.addEdge(support, employeur);
		graph.addEdge(support, enseignement);
		graph.addEdge(support, grade);
		graph.addEdge(support, labo);
		graph.addEdge(support, personne);
		graph.addEdge(support, typefinancement);
		graph.addEdge(support, typesupport);
		graph.addEdge(support, section);
		
		graph.addEdge(theme, slistediffusion);
		
		graph.addEdge(these, personne);
		graph.addEdge(these, personneexterieur);
		graph.addEdge(these, domainediplome);
		graph.addEdge(these, equipe);
		graph.addEdge(these, etablissement);
		graph.addEdge(these, personne);
		graph.addEdge(these, personne);
		graph.addEdge(these, sujet);
		
		graph.addEdge(zoom, scatzoom);
		graph.addEdge(zoom, scouleurzoom);
		graph.addEdge(zoom, actualite);
		
		TarjanSimpleCycles<String, DefaultEdge> trajanSimpleCycles = new TarjanSimpleCycles<>(graph);
		List<List<String>> cycles = trajanSimpleCycles.findSimpleCycles();
		
		System.out.println(cycles);

	}
}
