<?php
	use Restserver\Libraries\REST_Controller;

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	require APPPATH . 'libraries/REST_Controller.php';
	require APPPATH . 'libraries/Format.php';

	class LoginPerusahaan extends REST_Controller{

		public function __construct()
		{
			parent::__construct();
			$this->load->model('Perusahaan_model','perusahaan');
		}

		public function index_post(){
			$email = $this->post('Email');
			$password = $this->post('Password');
			$company = $this->perusahaan->getPerusahaanLogin($email,$password);
			if($company>0){
				$this->response([
					'status' => 200,
					'message' => "Login successful",
					'data' => $company
                ], REST_Controller::HTTP_OK ); 
			}
			else{
				$this->response([
					'status' => 200,
					'message' => "",
					'result' => null
                ], REST_Controller::HTTP_NOT_FOUND);
			}
		}
	}

?>
