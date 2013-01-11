#include "Particle.hpp"

MersenneTwister rnd;

Particle::Particle() {
  type=-1;
  eng=-1;
  pos=-1;
  parent_id=-1;
  samp1=-1;
  samp1_e=-1;
  samp1_pass=false;
}

Particle::Particle(int par_type, double par_eng,
		   double par_pos, double par_samp1,
		   int par_parent_id){
  type=par_type;
  eng=par_eng;
  pos=par_pos;
  parent_id=par_parent_id;
  samp1=par_samp1;
  samp1_e=-1;
  if(pos<samp1)
    samp1_pass=true;
  else
    samp1_pass=false;
}// end constructor

Particle::~Particle() {
}// end Destructor

double Particle::MeanFree(double lambda) {
  return -lambda*log(1-rnd.genRealOpen());
}// end MeanFree

double Particle::BremFunc(double x) {
  return 1-pow(x,0.2);
}

double Particle::Brem() {

  double func_val=-1,u=0,c=1,x=0;
  
  while(func_val<0) {
    u=rnd.genRealOpen();
    x=rnd.genRealOpen();
    if(u*c<BremFunc(x)) {
      func_val=x;
    }
  }
  return func_val;
}// end Brem

int Particle::GetType() {
  return type;
}// end GetType

double Particle::GetEng() {
  return eng;
}// end GetEng

void Particle::SetEng(double new_eng) {
  eng = new_eng;
}// end SetEng

double Particle::GetPos() {
  return pos;
}// end GetPos

void Particle::SetPos(double new_pos) {
  pos = new_pos;
}// end SetPos

int Particle::GetParent() {
  return parent_id;
}// end GetParent

double Particle::GetSamp1E() {
  return samp1_e;
}// end Samp1E

int Particle::Prop() {

  int r_type=-1;
  double temp_pos=0, temp_eng=0;

  if(type==0) {
    if(Particle::GetEng()>0.0) {
      if(Particle::GetEng()<10.0) {
	Particle::SetEng(0.0);
	r_type=3;
      }else {
	temp_pos=MeanFree(9.0/7.0)+Particle::GetPos();
	if(temp_pos>samp1 && samp1_pass) {
	  samp1_pass=false;
	  samp1_e=Particle::GetEng();
	}// end samp1 check
	if(temp_pos<ENDP){
	  Particle::SetPos(temp_pos);	
	  Particle::SetEng(0.0);
	  r_type=2;
	}
	else {
	  Particle::SetPos(ENDP);
	  r_type=4;
	}
      }// pair
    }else {
      r_type=5;
    }// end stopped
  }// gamma

  if(type==1) {
    if(Particle::GetEng()>0.0) {
      if(Particle::GetEng()<100.0) {
	temp_pos=1.0+Particle::GetPos();
	if(temp_pos>samp1 && samp1_pass) {
	  samp1_pass=false;
	  samp1_e=Particle::GetEng();
	}// end samp1 check
	if(temp_pos<ENDP){
	  Particle::SetPos(temp_pos);	
	  Particle::SetEng(0.0);
	  r_type=1;
	}
	else {
	Particle::SetPos(ENDP);
	r_type=4;
	}// end ion
      }else {
	temp_pos=MeanFree(0.28)+Particle::GetPos();
	if(temp_pos>samp1 && samp1_pass) {
	  samp1_pass=false;
	  samp1_e=Particle::GetEng();
	}// end samp1 check
	if(temp_pos<ENDP){
	  Particle::SetPos(temp_pos);
	  temp_eng = Particle::GetEng()-Particle::Brem()*Particle::GetEng();
	  Particle::SetEng(temp_eng);
	  r_type=0;
	}
	else {
	  Particle::SetPos(ENDP);
	  r_type=4;
	}
      }// end brem
    }else {
      r_type=5;
    }//end stopped
  }//electron
  
  return r_type;

}// end Prop

