package org.wiki.bot.exoplanetsbot;

import org.wiki.bot.util.Coordonnees;
import org.wiki.bot.util.Utils;

public class Planet
{
   private String name;
   private String mass;
   private String mass_error_min;
   private String mass_error_max;
   private String radius;
   private String radius_error_min;
   private String radius_error_max;
   private String orbital_period;
   private String orbital_period_error_min;
   private String orbital_period_error_max;
   private String semi_major_axis;
   private String semi_major_axis_error_min;
   private String semi_major_axis_error_max;
   private String eccentricity;
   private String eccentricity_error_min;
   private String eccentricity_error_max;
   private String inclination;
   private String inclination_error_min;
   private String inclination_error_max;
   private String angular_distance;
   private String discovered;
   private String updated;
   private String omega;
   private String omega_error_min;
   private String omega_error_max;
   private String tperi;
   private String tperi_error_min;
   private String tperi_error_max;
   private String tconj;
   private String tconj_error_min;
   private String tconj_error_max;
   private String tzero_tr;
   private String tzero_tr_error_min;
   private String tzero_tr_error_max;
   private String tzero_tr_sec;
   private String tzero_tr_sec_error_min;
   private String tzero_tr_sec_error_max;
   private String lambda_angle;
   private String lambda_angle_error_min;
   private String lambda_angle_error_max;
   private String impact_parameter;
   private String impact_parameter_error_min;
   private String impact_parameter_error_max;
   private String tzero_vr;
   private String tzero_vr_error_min;
   private String tzero_vr_error_max;
   private String k;
   private String k_error_min;
   private String k_error_max;
   private String temp_calculated;
   private String temp_measured;
   private String hot_point_lon;
   private String geometric_albedo;
   private String geometric_albedo_error_min;
   private String geometric_albedo_error_max;
   private String log_g;
   private String publication_status;
   private String detection_type;
   private String mass_detection_type;
   private String radius_detection_type;
   private String alternate_names;
   private String molecules;
   private String star_name;
   private String ra;
   private String dec;
   private String mag_v;
   private String mag_i;
   private String mag_j;
   private String mag_h;
   private String mag_k;
   private String star_distance;
   private String star_metallicity;
   private String star_mass;
   private String star_radius;
   private String star_sp_type;
   private String star_age;
   private String star_teff;
   private String star_detected_disc;
   private String star_magnetic_field;

   private String wikiArticle;
   private String erreurWiki;
   private String erreurEPE;
   
   public Planet(String[] tab)
   {
      this.name = tab[0];
      this.mass = tab[1];
      this.mass_error_min = tab[2];
      this.mass_error_max = tab[3];
      this.radius = tab[4];
      this.radius_error_min = tab[5];
      this.radius_error_max = tab[6];
      this.orbital_period = tab[7];
      this.orbital_period_error_min = tab[8];
      this.orbital_period_error_max = tab[9];
      this.semi_major_axis = tab[10];
      this.semi_major_axis_error_min = tab[11];
      this.semi_major_axis_error_max = tab[12];
      this.eccentricity = tab[13];
      this.eccentricity_error_min = tab[14];
      this.eccentricity_error_max = tab[15];
      this.inclination = tab[16];
      this.inclination_error_min = tab[17];
      this.inclination_error_max = tab[18];
      this.angular_distance = tab[19];
      this.discovered = tab[20];
      this.updated = tab[21];
      this.omega = tab[22];
      this.omega_error_min = tab[23];
      this.omega_error_max = tab[24];
      this.tperi = tab[25];
      this.tperi_error_min = tab[26];
      this.tperi_error_max = tab[27];
      this.tconj = tab[28];
      this.tconj_error_min = tab[29];
      this.tconj_error_max = tab[30];
      this.tzero_tr = tab[31];
      this.tzero_tr_error_min = tab[32];
      this.tzero_tr_error_max = tab[33];
      this.tzero_tr_sec = tab[34];
      this.tzero_tr_sec_error_min = tab[35];
      this.tzero_tr_sec_error_max = tab[36];
      this.lambda_angle = tab[37];
      this.lambda_angle_error_min = tab[38];
      this.lambda_angle_error_max = tab[39];
      this.impact_parameter = tab[40];
      this.impact_parameter_error_min = tab[41];
      this.impact_parameter_error_max = tab[42];
      this.tzero_vr = tab[43];
      this.tzero_vr_error_min = tab[44];
      this.tzero_vr_error_max = tab[45];
      this.k = tab[46];
      this.k_error_min = tab[47];
      this.k_error_max = tab[48];
      this.temp_calculated = tab[49];
      this.temp_measured = tab[50];
      this.hot_point_lon = tab[51];
      this.geometric_albedo = tab[52];
      this.geometric_albedo_error_min = tab[53];
      this.geometric_albedo_error_max = tab[54];
      this.log_g = tab[55];
      this.publication_status = tab[56];
      this.detection_type = tab[57];
      this.mass_detection_type = tab[58];
      this.radius_detection_type = tab[59];
      this.alternate_names = tab[60];
      this.molecules = tab[61];
      this.star_name = tab[62];
      
      if (tab[63].matches("[+-]*[0-9]+\\.[0-9]+"))
      {
         this.ra = tab[63]; /* ascencion droite */
      } else
      {
         this.erreurEPE = tab[63] + " n'est pas une valeur AD valide";
      }
      if (tab[64].matches("[+-]*[0-9]+\\.[0-9]+"))
      {
         this.dec = tab[64]; /* declinaison */
      } else
      {
         this.erreurEPE = tab[64] + " n'est pas une valeur AD valide";
      }
      /*
      this.ra = Utils.decimal2Degrees(Double.valueOf(tab[63]));
      this.dec = Utils.decimal2Degrees(Double.valueOf(tab[64]));
      */
      /*
      this.mag_v = tab[65];
      this.mag_i = tab[66];
      this.mag_j = tab[67];
      this.mag_h = tab[68];
      this.mag_k = tab[69];
      this.star_distance = tab[70];
      this.star_metallicity = tab[71];
      this.star_mass = tab[72];
      this.star_radius = tab[73];
      this.star_sp_type = tab[74];
      this.star_age = tab[75];
      this.star_teff = tab[76];
      this.star_detected_disc = tab[77];
      this.star_magnetic_field = tab[78];
      */
   }

   public Planet()
   {
      // TODO Auto-generated constructor stub
   }

   public String getWikiArticle()
   {
      return wikiArticle;
   }

   public void setWikiArticle(String wikiArticle)
   {
      this.wikiArticle = wikiArticle;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getMass()
   {
      return mass;
   }

   public void setMass(String mass)
   {
      this.mass = mass;
   }

   public String getMass_error_min()
   {
      return mass_error_min;
   }

   public void setMass_error_min(String mass_error_min)
   {
      this.mass_error_min = mass_error_min;
   }

   public String getMass_error_max()
   {
      return mass_error_max;
   }

   public void setMass_error_max(String mass_error_max)
   {
      this.mass_error_max = mass_error_max;
   }

   public String getRadius()
   {
      return radius;
   }

   public void setRadius(String radius)
   {
      this.radius = radius;
   }

   public String getRadius_error_min()
   {
      return radius_error_min;
   }

   public void setRadius_error_min(String radius_error_min)
   {
      this.radius_error_min = radius_error_min;
   }

   public String getRadius_error_max()
   {
      return radius_error_max;
   }

   public void setRadius_error_max(String radius_error_max)
   {
      this.radius_error_max = radius_error_max;
   }

   public String getOrbital_period()
   {
      return orbital_period;
   }

   public void setOrbital_period(String orbital_period)
   {
      this.orbital_period = orbital_period;
   }

   public String getOrbital_period_error_min()
   {
      return orbital_period_error_min;
   }

   public void setOrbital_period_error_min(String orbital_period_error_min)
   {
      this.orbital_period_error_min = orbital_period_error_min;
   }

   public String getOrbital_period_error_max()
   {
      return orbital_period_error_max;
   }

   public void setOrbital_period_error_max(String orbital_period_error_max)
   {
      this.orbital_period_error_max = orbital_period_error_max;
   }

   public String getSemi_major_axis()
   {
      return semi_major_axis;
   }

   public void setSemi_major_axis(String semi_major_axis)
   {
      this.semi_major_axis = semi_major_axis;
   }

   public String getSemi_major_axis_error_min()
   {
      return semi_major_axis_error_min;
   }

   public void setSemi_major_axis_error_min(String semi_major_axis_error_min)
   {
      this.semi_major_axis_error_min = semi_major_axis_error_min;
   }

   public String getSemi_major_axis_error_max()
   {
      return semi_major_axis_error_max;
   }

   public void setSemi_major_axis_error_max(String semi_major_axis_error_max)
   {
      this.semi_major_axis_error_max = semi_major_axis_error_max;
   }

   public String getEccentricity()
   {
      return eccentricity;
   }

   public void setEccentricity(String eccentricity)
   {
      this.eccentricity = eccentricity;
   }

   public String getEccentricity_error_min()
   {
      return eccentricity_error_min;
   }

   public void setEccentricity_error_min(String eccentricity_error_min)
   {
      this.eccentricity_error_min = eccentricity_error_min;
   }

   public String getEccentricity_error_max()
   {
      return eccentricity_error_max;
   }

   public void setEccentricity_error_max(String eccentricity_error_max)
   {
      this.eccentricity_error_max = eccentricity_error_max;
   }

   public String getInclination()
   {
      return inclination;
   }

   public void setInclination(String inclination)
   {
      this.inclination = inclination;
   }

   public String getInclination_error_min()
   {
      return inclination_error_min;
   }

   public void setInclination_error_min(String inclination_error_min)
   {
      this.inclination_error_min = inclination_error_min;
   }

   public String getInclination_error_max()
   {
      return inclination_error_max;
   }

   public void setInclination_error_max(String inclination_error_max)
   {
      this.inclination_error_max = inclination_error_max;
   }

   public String getAngular_distance()
   {
      return angular_distance;
   }

   public void setAngular_distance(String angular_distance)
   {
      this.angular_distance = angular_distance;
   }

   public String getDiscovered()
   {
      return discovered;
   }

   public void setDiscovered(String discovered)
   {
      this.discovered = discovered;
   }

   public String getUpdated()
   {
      return updated;
   }

   public void setUpdated(String updated)
   {
      this.updated = updated;
   }

   public String getOmega()
   {
      return omega;
   }

   public void setOmega(String omega)
   {
      this.omega = omega;
   }

   public String getOmega_error_min()
   {
      return omega_error_min;
   }

   public void setOmega_error_min(String omega_error_min)
   {
      this.omega_error_min = omega_error_min;
   }

   public String getOmega_error_max()
   {
      return omega_error_max;
   }

   public void setOmega_error_max(String omega_error_max)
   {
      this.omega_error_max = omega_error_max;
   }

   public String getTperi()
   {
      return tperi;
   }

   public void setTperi(String tperi)
   {
      this.tperi = tperi;
   }

   public String getTperi_error_min()
   {
      return tperi_error_min;
   }

   public void setTperi_error_min(String tperi_error_min)
   {
      this.tperi_error_min = tperi_error_min;
   }

   public String getTperi_error_max()
   {
      return tperi_error_max;
   }

   public void setTperi_error_max(String tperi_error_max)
   {
      this.tperi_error_max = tperi_error_max;
   }

   public String getTconj()
   {
      return tconj;
   }

   public void setTconj(String tconj)
   {
      this.tconj = tconj;
   }

   public String getTconj_error_min()
   {
      return tconj_error_min;
   }

   public void setTconj_error_min(String tconj_error_min)
   {
      this.tconj_error_min = tconj_error_min;
   }

   public String getTconj_error_max()
   {
      return tconj_error_max;
   }

   public void setTconj_error_max(String tconj_error_max)
   {
      this.tconj_error_max = tconj_error_max;
   }

   public String getTzero_tr()
   {
      return tzero_tr;
   }

   public void setTzero_tr(String tzero_tr)
   {
      this.tzero_tr = tzero_tr;
   }

   public String getTzero_tr_error_min()
   {
      return tzero_tr_error_min;
   }

   public void setTzero_tr_error_min(String tzero_tr_error_min)
   {
      this.tzero_tr_error_min = tzero_tr_error_min;
   }

   public String getTzero_tr_error_max()
   {
      return tzero_tr_error_max;
   }

   public void setTzero_tr_error_max(String tzero_tr_error_max)
   {
      this.tzero_tr_error_max = tzero_tr_error_max;
   }

   public String getTzero_tr_sec()
   {
      return tzero_tr_sec;
   }

   public void setTzero_tr_sec(String tzero_tr_sec)
   {
      this.tzero_tr_sec = tzero_tr_sec;
   }

   public String getTzero_tr_sec_error_min()
   {
      return tzero_tr_sec_error_min;
   }

   public void setTzero_tr_sec_error_min(String tzero_tr_sec_error_min)
   {
      this.tzero_tr_sec_error_min = tzero_tr_sec_error_min;
   }

   public String getTzero_tr_sec_error_max()
   {
      return tzero_tr_sec_error_max;
   }

   public void setTzero_tr_sec_error_max(String tzero_tr_sec_error_max)
   {
      this.tzero_tr_sec_error_max = tzero_tr_sec_error_max;
   }

   public String getLambda_angle()
   {
      return lambda_angle;
   }

   public void setLambda_angle(String lambda_angle)
   {
      this.lambda_angle = lambda_angle;
   }

   public String getLambda_angle_error_min()
   {
      return lambda_angle_error_min;
   }

   public void setLambda_angle_error_min(String lambda_angle_error_min)
   {
      this.lambda_angle_error_min = lambda_angle_error_min;
   }

   public String getLambda_angle_error_max()
   {
      return lambda_angle_error_max;
   }

   public void setLambda_angle_error_max(String lambda_angle_error_max)
   {
      this.lambda_angle_error_max = lambda_angle_error_max;
   }

   public String getImpact_parameter()
   {
      return impact_parameter;
   }

   public void setImpact_parameter(String impact_parameter)
   {
      this.impact_parameter = impact_parameter;
   }

   public String getImpact_parameter_error_min()
   {
      return impact_parameter_error_min;
   }

   public void setImpact_parameter_error_min(String impact_parameter_error_min)
   {
      this.impact_parameter_error_min = impact_parameter_error_min;
   }

   public String getImpact_parameter_error_max()
   {
      return impact_parameter_error_max;
   }

   public void setImpact_parameter_error_max(String impact_parameter_error_max)
   {
      this.impact_parameter_error_max = impact_parameter_error_max;
   }

   public String getTzero_vr()
   {
      return tzero_vr;
   }

   public void setTzero_vr(String tzero_vr)
   {
      this.tzero_vr = tzero_vr;
   }

   public String getTzero_vr_error_min()
   {
      return tzero_vr_error_min;
   }

   public void setTzero_vr_error_min(String tzero_vr_error_min)
   {
      this.tzero_vr_error_min = tzero_vr_error_min;
   }

   public String getTzero_vr_error_max()
   {
      return tzero_vr_error_max;
   }

   public void setTzero_vr_error_max(String tzero_vr_error_max)
   {
      this.tzero_vr_error_max = tzero_vr_error_max;
   }

   public String getK()
   {
      return k;
   }

   public void setK(String k)
   {
      this.k = k;
   }

   public String getK_error_min()
   {
      return k_error_min;
   }

   public void setK_error_min(String k_error_min)
   {
      this.k_error_min = k_error_min;
   }

   public String getK_error_max()
   {
      return k_error_max;
   }

   public void setK_error_max(String k_error_max)
   {
      this.k_error_max = k_error_max;
   }

   public String getTemp_calculated()
   {
      return temp_calculated;
   }

   public void setTemp_calculated(String temp_calculated)
   {
      this.temp_calculated = temp_calculated;
   }

   public String getTemp_measured()
   {
      return temp_measured;
   }

   public void setTemp_measured(String temp_measured)
   {
      this.temp_measured = temp_measured;
   }

   public String getHot_point_lon()
   {
      return hot_point_lon;
   }

   public void setHot_point_lon(String hot_point_lon)
   {
      this.hot_point_lon = hot_point_lon;
   }

   public String getGeometric_albedo()
   {
      return geometric_albedo;
   }

   public void setGeometric_albedo(String geometric_albedo)
   {
      this.geometric_albedo = geometric_albedo;
   }

   public String getGeometric_albedo_error_min()
   {
      return geometric_albedo_error_min;
   }

   public void setGeometric_albedo_error_min(String geometric_albedo_error_min)
   {
      this.geometric_albedo_error_min = geometric_albedo_error_min;
   }

   public String getGeometric_albedo_error_max()
   {
      return geometric_albedo_error_max;
   }

   public void setGeometric_albedo_error_max(String geometric_albedo_error_max)
   {
      this.geometric_albedo_error_max = geometric_albedo_error_max;
   }

   public String getLog_g()
   {
      return log_g;
   }

   public void setLog_g(String log_g)
   {
      this.log_g = log_g;
   }

   public String getPublication_status()
   {
      return publication_status;
   }

   public void setPublication_status(String publication_status)
   {
      this.publication_status = publication_status;
   }

   public String getDetection_type()
   {
      return detection_type;
   }

   public void setDetection_type(String detection_type)
   {
      this.detection_type = detection_type;
   }

   public String getMass_detection_type()
   {
      return mass_detection_type;
   }

   public void setMass_detection_type(String mass_detection_type)
   {
      this.mass_detection_type = mass_detection_type;
   }

   public String getRadius_detection_type()
   {
      return radius_detection_type;
   }

   public void setRadius_detection_type(String radius_detection_type)
   {
      this.radius_detection_type = radius_detection_type;
   }

   public String getAlternate_names()
   {
      return alternate_names;
   }

   public void setAlternate_names(String alternate_names)
   {
      this.alternate_names = alternate_names;
   }

   public String getMolecules()
   {
      return molecules;
   }

   public void setMolecules(String molecules)
   {
      this.molecules = molecules;
   }

   public String getStar_name()
   {
      return star_name;
   }

   public void setStar_name(String star_name)
   {
      this.star_name = star_name;
   }

   public String getRa()
   {
      return ra;
   }

   public void setRa(String ra)
   {
      this.ra = ra;
   }

   public String getDec()
   {
      return dec;
   }

   public void setDec(String dec)
   {
      this.dec = dec;
   }

   public String getMag_v()
   {
      return mag_v;
   }

   public void setMag_v(String mag_v)
   {
      this.mag_v = mag_v;
   }

   public String getMag_i()
   {
      return mag_i;
   }

   public void setMag_i(String mag_i)
   {
      this.mag_i = mag_i;
   }

   public String getMag_j()
   {
      return mag_j;
   }

   public void setMag_j(String mag_j)
   {
      this.mag_j = mag_j;
   }

   public String getMag_h()
   {
      return mag_h;
   }

   public void setMag_h(String mag_h)
   {
      this.mag_h = mag_h;
   }

   public String getMag_k()
   {
      return mag_k;
   }

   public void setMag_k(String mag_k)
   {
      this.mag_k = mag_k;
   }

   public String getStar_distance()
   {
      return star_distance;
   }

   public void setStar_distance(String star_distance)
   {
      this.star_distance = star_distance;
   }

   public String getStar_metallicity()
   {
      return star_metallicity;
   }

   public void setStar_metallicity(String star_metallicity)
   {
      this.star_metallicity = star_metallicity;
   }

   public String getStar_mass()
   {
      return star_mass;
   }

   public void setStar_mass(String star_mass)
   {
      this.star_mass = star_mass;
   }

   public String getStar_radius()
   {
      return star_radius;
   }

   public void setStar_radius(String star_radius)
   {
      this.star_radius = star_radius;
   }

   public String getStar_sp_type()
   {
      return star_sp_type;
   }

   public void setStar_sp_type(String star_sp_type)
   {
      this.star_sp_type = star_sp_type;
   }

   public String getStar_age()
   {
      return star_age;
   }

   public void setStar_age(String star_age)
   {
      this.star_age = star_age;
   }

   public String getStar_teff()
   {
      return star_teff;
   }

   public void setStar_teff(String star_teff)
   {
      this.star_teff = star_teff;
   }

   public String getStar_detected_disc()
   {
      return star_detected_disc;
   }

   public void setStar_detected_disc(String star_detected_disc)
   {
      this.star_detected_disc = star_detected_disc;
   }

   public String getStar_magnetic_field()
   {
      return star_magnetic_field;
   }

   public void setStar_magnetic_field(String star_magnetic_field)
   {
      this.star_magnetic_field = star_magnetic_field;
   }

   public String getErreurWiki()
   {
      return erreurWiki;
   }

   public void setErreurWiki(String erreurWiki)
   {
      this.erreurWiki = erreurWiki;
   }

   @Override
   public String toString()
   {
      return "Planet [wikiArticle=" + wikiArticle + ", name=" + name + ", mass=" + mass + ", mass_error_min=" + mass_error_min
               + ", mass_error_max=" + mass_error_max + ", radius=" + radius
               + ", radius_error_min=" + radius_error_min + ", radius_error_max="
               + radius_error_max + ", orbital_period=" + orbital_period
               + ", orbital_period_error_min=" + orbital_period_error_min
               + ", orbital_period_error_max=" + orbital_period_error_max + ", semi_major_axis="
               + semi_major_axis + ", semi_major_axis_error_min=" + semi_major_axis_error_min
               + ", semi_major_axis_error_max=" + semi_major_axis_error_max + ", eccentricity="
               + eccentricity + ", eccentricity_error_min=" + eccentricity_error_min
               + ", eccentricity_error_max=" + eccentricity_error_max + ", inclination="
               + inclination + ", inclination_error_min=" + inclination_error_min
               + ", inclination_error_max=" + inclination_error_max + ", angular_distance="
               + angular_distance + ", discovered=" + discovered + ", updated=" + updated
               + ", omega=" + omega + ", omega_error_min=" + omega_error_min + ", omega_error_max="
               + omega_error_max + ", tperi=" + tperi + ", tperi_error_min=" + tperi_error_min
               + ", tperi_error_max=" + tperi_error_max + ", tconj=" + tconj + ", tconj_error_min="
               + tconj_error_min + ", tconj_error_max=" + tconj_error_max + ", tzero_tr="
               + tzero_tr + ", tzero_tr_error_min=" + tzero_tr_error_min + ", tzero_tr_error_max="
               + tzero_tr_error_max + ", tzero_tr_sec=" + tzero_tr_sec
               + ", tzero_tr_sec_error_min=" + tzero_tr_sec_error_min + ", tzero_tr_sec_error_max="
               + tzero_tr_sec_error_max + ", lambda_angle=" + lambda_angle
               + ", lambda_angle_error_min=" + lambda_angle_error_min + ", lambda_angle_error_max="
               + lambda_angle_error_max + ", impact_parameter=" + impact_parameter
               + ", impact_parameter_error_min=" + impact_parameter_error_min
               + ", impact_parameter_error_max=" + impact_parameter_error_max + ", tzero_vr="
               + tzero_vr + ", tzero_vr_error_min=" + tzero_vr_error_min + ", tzero_vr_error_max="
               + tzero_vr_error_max + ", k=" + k + ", k_error_min=" + k_error_min
               + ", k_error_max=" + k_error_max + ", temp_calculated=" + temp_calculated
               + ", temp_measured=" + temp_measured + ", hot_point_lon=" + hot_point_lon
               + ", geometric_albedo=" + geometric_albedo + ", geometric_albedo_error_min="
               + geometric_albedo_error_min + ", geometric_albedo_error_max="
               + geometric_albedo_error_max + ", log_g=" + log_g + ", publication_status="
               + publication_status + ", detection_type=" + detection_type
               + ", mass_detection_type=" + mass_detection_type + ", radius_detection_type="
               + radius_detection_type + ", alternate_names=" + alternate_names + ", molecules="
               + molecules + ", star_name=" + star_name + ", ra=" + ra + ", dec=" + dec
               + ", mag_v=" + mag_v + ", mag_i=" + mag_i + ", mag_j=" + mag_j + ", mag_h=" + mag_h
               + ", mag_k=" + mag_k + ", star_distance=" + star_distance + ", star_metallicity="
               + star_metallicity + ", star_mass=" + star_mass + ", star_radius=" + star_radius
               + ", star_sp_type=" + star_sp_type + ", star_age=" + star_age + ", star_teff="
               + star_teff + ", star_detected_disc=" + star_detected_disc
               + ", star_magnetic_field=" + star_magnetic_field + "]";
   }
   
}